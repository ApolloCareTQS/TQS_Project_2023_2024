package com.apollocare.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apollocare.backend.models.Appointment;
import com.apollocare.backend.service.AppoimentService;

@RestController
public class ApolloController {

    private final AppoimentService apService;

    public ApolloController(AppoimentService apService) {
        this.apService = apService;
    }

    @GetMapping("/appointments")
    public List<Appointment> getAllAppointments() {
        return apService.findAllAppointments();
    }

    @PostMapping("/add")
    public Appointment addAppointment(@RequestBody Appointment appointment) {
        return apService.schedule(appointment);
    }

    @GetMapping("/appointments/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return apService.getAppointmentById(id).orElse(null);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        apService.deleteAppointment(id);
    }

}
