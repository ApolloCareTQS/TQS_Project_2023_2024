package com.apollocare.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.models.Doctor;
import com.apollocare.backend.service.ConsultationService;

@Controller
@RequestMapping("/admin/v1")
public class AdminController {

    private final ConsultationService cService;

    public AdminController(ConsultationService cService) {
        this.cService = cService;
    }

    @GetMapping("/all")
    public String getAllConsultations(Model model) {
        List<Consultation> consultations = cService.findAllConsultations();
        model.addAttribute("consultations", consultations);
        return "consultations";
    }

    @GetMapping("/form")
    public String showAddConsultationForm(Model model) {
        List<Doctor> doctors = cService.findAllDoctors();
        model.addAttribute("doctors", doctors);
        model.addAttribute("consultation", new Consultation());
        return "add";
    }

    @PostMapping("/add")
    public ResponseEntity<Consultation> addConsultation(@ModelAttribute Consultation consultation) {
        Optional<Consultation> optionalConsultation = cService.schedule(consultation);
        return optionalConsultation.map(ResponseEntity::ok)
                                   .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    @PostMapping("/checkin")
    public String checkInConsultation(@RequestParam("id") Long id) {
        cService.checkInConsultation(id);
        return "redirect:/admin/v1/all";
    }

    @GetMapping("/consultations/{id}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable Long id) {
        Optional<Consultation> optionalConsultation = cService.getConsultationById(id);
        return optionalConsultation.map(ResponseEntity::ok)
                                   .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
        cService.deleteConsultation(id);
        return ResponseEntity.noContent().build();
    }
}
