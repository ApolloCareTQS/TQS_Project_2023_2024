package com.apollocare.backend.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.models.ConsultationRepo;
import com.apollocare.backend.models.Doctor;
import com.apollocare.backend.models.DoctorRepo;
import com.apollocare.backend.models.PatientRepo;
import com.apollocare.backend.models.Patient;


@Service
public class ConsultationService {

    private static final Logger logger=LogManager.getLogger();
    private ConsultationRepo consultationRepo;
    private DoctorRepo doctorRepo;
    private PatientRepo patientRepo;

    public ConsultationService( ConsultationRepo consultationRepo, DoctorRepo doctorRepo, PatientRepo patientRepo) {
        this.consultationRepo = consultationRepo; 
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
    }



    public List<Doctor> findAllDoctors() {
        logger.info("Finding all doctors");
        return doctorRepo.findAll();
    }

    public List<Consultation> findAllConsultations() {
        logger.info("Finding all consultations");
        return consultationRepo.findAll();
    }

    public Optional<Consultation> schedule(Consultation consultation) {
        try {
            Optional<Doctor> d = doctorRepo.findById(consultation.getDoctorId());
            Optional<Patient> p = patientRepo.findById(consultation.getPatientId());
            if (d.isPresent() && p.isPresent()) {
                logger.info("Scheduling consultation for doctor ID {} and patient ID {}", consultation.getDoctorId(), consultation.getPatientId());
                return consultationRepo.insert(consultation);
            } else {
                logger.error("Failed to schedule consultation. Doctor or patient not found.");
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("Error while scheduling consultation: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Consultation> getConsultationById(Long id) {
        try {
            logger.info("Getting consultation by ID: {}", id);
            return consultationRepo.findById(id);
        } catch (Exception e) {
            logger.error("Error while getting consultation by ID {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    public void checkInConsultation(Long id) {
        try {
            Optional<Consultation> c = consultationRepo.findById(id);
            if (c.isPresent()) {
                Consultation consultation = c.get();
                consultation.setCheckInDate(System.currentTimeMillis());
                consultation.setState("CHECKED_IN");
                consultationRepo.update(consultation);
                logger.info("Consultation checked in successfully: ID {}", id);
            } else {
                logger.error("Consultation with ID {} not found for check-in", id);
            }
        } catch (Exception e) {
            logger.error("Error while checking in consultation with ID {}: {}", id, e.getMessage());
        }
    }

    public void deleteConsultation(Long id){
    }
}
