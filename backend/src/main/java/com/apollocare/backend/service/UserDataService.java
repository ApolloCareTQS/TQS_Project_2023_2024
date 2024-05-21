package com.apollocare.backend.service;

import static com.apollocare.backend.util.State.CHECKED_OUT;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.models.ConsultationRepo;

@Service
public class UserDataService {
    private ConsultationRepo consultationRepo;
    private static final Logger logger=LogManager.getLogger();

    public UserDataService(ConsultationRepo consultationRepo){
        this.consultationRepo=consultationRepo;
    }

    public List<Consultation> getConsultationHistory(String patientId){
        return consultationRepo.findAllByPatientIDAndState(patientId, CHECKED_OUT);
    }
}
