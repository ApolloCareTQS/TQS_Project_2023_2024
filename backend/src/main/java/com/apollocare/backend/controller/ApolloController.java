/*
package com.apollocare.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.service.ConsultationService;

@RestController
@RequestMapping("/api/v1")
public class ApolloController {

    private final ConsultationService cService;

    public ApolloController(ConsultationService cService) {
        this.cService = cService;
    }

    @GetMapping("/consultations")
    public List<Consultation> getAllConsultations() {
        return cService.findAllConsultations();
    }
    //TODO: change to ResponseEntity<...> everything that might return a non-500 error (mainly post requests). Also, non-list values will be returned as Optional's to avoid nulls, so properly handle them (if getting muyltiple values, an empty list will be returned instead)
    @PostMapping("/add")
    public Consultation addConsultation(@RequestBody Consultation Consultation) {
        return cService.schedule(Consultation).get();
    }

    @GetMapping("/consultations/{id}")
    public Consultation getConsultationById(@PathVariable Long id) {
        return cService.getConsultationById(id).get();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteConsultation(@PathVariable Long id) {
        cService.deleteConsultation(id);
    }

}
*/