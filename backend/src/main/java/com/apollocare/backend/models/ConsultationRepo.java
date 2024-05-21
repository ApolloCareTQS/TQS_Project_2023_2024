package com.apollocare.backend.models;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.apollocare.backend.util.State;
import com.apollocare.backend.util.SupabaseManager;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class ConsultationRepo extends Repository{

    public ConsultationRepo(SupabaseManager manager) {
        super(manager);
    }


    public Optional<Consultation> insert(Consultation consultation) throws JsonProcessingException{
        Map<String, Object> body = new HashMap<>();
        body.put("scheduledDate", consultation.getScheduledDate());
        body.put("patientId", consultation.getPatientId());
        body.put("doctorId", consultation.getDoctorId());
        body.put("specialty", consultation.getSpecialty());
        body.put("location", consultation.getLocation());
        logger.debug("id: {} -> insert called",consultation.getId());
        ResponseEntity<String> response=manager.postRequest("rest/v1/Consultation", mapper.writeValueAsString(body));

        if(response.getStatusCode()!=HttpStatus.CREATED){
            logger.debug("id: {} -> insert failed",consultation.getId());
            return Optional.empty();
        }else{
            return Optional.of(consultation);
        }
    }

    public Optional<Consultation> update(Consultation consultation) throws JsonProcessingException {
        Map<String, Object> body = new HashMap<>();
        body.put("scheduledDate", consultation.getScheduledDate());
        body.put("checkInDate", consultation.getCheckInDate());
        body.put("receptionDate", consultation.getReceptionDate());
        body.put("duration", consultation.getDuration());
        body.put("patientId", consultation.getPatientId());
        body.put("doctorId", consultation.getDoctorId());
        body.put("state", consultation.getState());
        body.put("specialty", consultation.getSpecialty());
        body.put("location", consultation.getLocation());
    
        String uri = "rest/v1/Consultation?id=eq." + consultation.getId();
    
        logger.debug("id: {}\n" + //
                        " -> update called", consultation.getId());
        ResponseEntity<String> response = manager.patchRequest(uri, mapper.writeValueAsString(body));
    
        if (response.getStatusCode() != HttpStatus.OK) {
            logger.debug("id: {} -> update failed", consultation.getId());
            return Optional.empty();
        } else {
            return Optional.of(consultation);
        }
    }

    public Optional<Consultation> findById(Long id) throws JsonProcessingException{
        ResponseEntity<String> response=manager.getRequest("rest/v1/Consultation?select=*&id=eq."+id);
        if(response.getStatusCode()!=HttpStatus.OK){
            logger.debug("id: {} -> findById() request returned with code {}",id,response.getStatusCode());
            return Optional.empty();
        }else{
            String body=response.getBody();
            logger.debug("id: {} -> findById() request returned with body {}",id,body);
            Consultation[] consultations=mapper.readValue(body,Consultation[].class);
            if(consultations.length==0){
                logger.debug("id: {} -> findById() had no results",id);
                return Optional.empty();
            }else{
                return Optional.of(consultations[0]);
            }
        }
    }

    public List<Consultation> findAll() {
        ResponseEntity<String> response = manager.getRequest("/rest/v1/Consultation?select=*");
        if (response.getStatusCode() == HttpStatus.OK) {
            return manager.parseConsultationList(response.getBody());
        }
        return Collections.emptyList();
    }

    public List<Consultation> findAllByPatientIDAndState(String patientId, State state){
        ResponseEntity<String> response=manager.getRequest("/rest/v1/Consultation?and=(patientId.eq."+patientId+",state.eq."+state.name()+")&select=*");
        if(response.getStatusCode()==HttpStatus.OK){
            return manager.parseConsultationList(response.getBody());
        }else{
            return new ArrayList<>();
        }
    }
    
}
