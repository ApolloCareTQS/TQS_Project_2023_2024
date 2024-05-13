package com.apollocare.backend.models;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.apollocare.backend.util.SupabaseManager;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class PatientRepo extends Repository{
    public PatientRepo(SupabaseManager manager) {
        super(manager);
    }

    public Optional<Patient> findById(String id) throws JsonProcessingException{
        ResponseEntity<String> response=manager.getRequest("rest/v1/Patient?select=*&id=eq."+id);
        if(response.getStatusCode()!=HttpStatus.OK){
            logger.debug("id: {} -> findById() request returned with code {}",id,response.getStatusCode());
            return Optional.empty();
        }else{
            String body=response.getBody();
            logger.debug("id: {} -> findById() request returned with body {}",id,body);
            Patient[] patients=mapper.readValue(body,Patient[].class);
            if(patients.length==0){
                logger.debug("id: {} -> findById() had no results",id);
                return Optional.empty();
            }else{
                return Optional.of(patients[0]);
            }
        }
    }

    public Optional<Patient> insert(Patient user) {
        return null;
    }
}
