package com.apollocare.backend.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.apollocare.backend.util.SupabaseManager;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class DoctorRepo extends Repository {

    public DoctorRepo(SupabaseManager manager) {
        super(manager);
    }

    public Optional<Doctor> findById(String id) throws JsonProcessingException{
        ResponseEntity<String> response=manager.getRequest("rest/v1/Doctor?select=*&id=eq."+id);
        if(response.getStatusCode()!=HttpStatus.OK){
            logger.debug("id: {} -> findById() request returned with code {}",id,response.getStatusCode());
            return Optional.empty();
        }else{
            String body=response.getBody();
            logger.debug("id: {} -> findById() request returned with body {}",id,body);
            Doctor[] doctors=mapper.readValue(body,Doctor[].class);
            if(doctors.length==0){
                logger.debug("id: {} -> findById() had no results",id);
                return Optional.empty();
            }else{
                return Optional.of(doctors[0]);
            }
        }
    }

    public Optional<Doctor> insert(Doctor doctor) throws JsonProcessingException{
        Map<String,String> body=new HashMap<>();
        body.put("id",doctor.getId());
        body.put("email",doctor.getEmail());
        body.put("name",doctor.getEmail());
        body.put("clinic",doctor.getClinic());
        body.put("specialty",doctor.getSpecialty());
        logger.debug("id: {} -> insert called",doctor.getId());
        ResponseEntity<String> response=manager.postRequest("rest/v1/Patient", mapper.writeValueAsString(body));

        if(response.getStatusCode()!=HttpStatus.CREATED){
            logger.debug("id: {} -> insert failed",doctor.getId());
            return Optional.empty();
        }else{
            return Optional.of(doctor);
        }
    }

    
    
}
