package com.apollocare.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.apollocare.backend.models.*;

@Service
public class AppoitmentService {
    
    private final AppointmentRepo repo;

    public AppoimentService(AppointmentRepo repo) {
        this.repo = repo;
    }

    public Appointment schedule(Appointment a) {
        return repo.save(a);
    }

    public List<Appointment> findAllAppointments() {
        return repo.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return repo.findById(id);
    }

    public void deleteAppointment(Long id) {
        repo.deleteById(id);
    }
}
