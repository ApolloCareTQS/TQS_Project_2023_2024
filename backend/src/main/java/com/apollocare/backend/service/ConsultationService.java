package com.apollocare.backend.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.models.ConsultationRepo;
import com.apollocare.backend.util.SupabaseManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsultationService {

    private SupabaseManager manager;
    private ObjectMapper mapper;
    private static final Logger logger=LogManager.getLogger();
    private ConsultationRepo ConsultationRepo;

    public ConsultationService(SupabaseManager manager, ConsultationRepo ConsultationRepo) {
        this.manager = manager;
        this.mapper = new ObjectMapper();
        this.ConsultationRepo = ConsultationRepo; 
    }




    public List<Consultation> findAllConsultations(){
        return ConsultationRepo.findAll();
    }

    public Optional<Consultation> schedule(Consultation consultation) {
        try {
            return ConsultationRepo.insert(consultation);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Consultation> getConsultationById(String id) {
        try {
            return ConsultationRepo.findById(id);
        } catch (Exception e) {
            // Log error
            return Optional.empty();
        }
    }

    public void deleteConsultation(Long id){
    }
}


// package com.apollocare.backend.service;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.stereotype.Service;

// import com.apollocare.backend.models.*;

// @Service
// public class AppoimentService {
    
//     private final AppointmentRepo repo;

//     public AppoimentService(AppointmentRepo repo) {
//         this.repo = repo;
//     }

//     public Appointment schedule(Appointment a) {
//         return repo.save(a);
//     }

//     public List<Appointment> findAllAppointments() {
//         return repo.findAll();
//     }

//     public Optional<Appointment> getAppointmentById(Long id) {
//         return repo.findById(id);
//     }

//     public void deleteAppointment(Long id) {
//         repo.deleteById(id);
//     }
// }
