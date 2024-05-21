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
public class StaffRepo extends Repository {

    public StaffRepo(SupabaseManager manager) {
        super(manager);
    }

    public Optional<Staff> insert(Staff staff) throws JsonProcessingException{
        Map<String,String> body=new HashMap<>();
        body.put("id",staff.getId());
        body.put("email",staff.getEmail());
        body.put("name",staff.getEmail());
        body.put("clinic",staff.getClinic());
        logger.debug("id: {} -> insert called",staff.getId());
        ResponseEntity<String> response=manager.postRequest("rest/v1/Staff", mapper.writeValueAsString(body));

        if(response.getStatusCode()!=HttpStatus.CREATED){
            logger.debug("id: {} -> insert failed",staff.getId());
            return Optional.empty();
        }else{
            return Optional.of(staff);
        }
    }

    public Optional<Staff> findById(String id) throws JsonProcessingException{
        ResponseEntity<String> response=manager.getRequest("rest/v1/Staff?select=*&id=eq."+id);
        if(response.getStatusCode()!=HttpStatus.OK){
            logger.debug("id: {} -> findById() request returned with code {}",id,response.getStatusCode());
            return Optional.empty();
        }else{
            String body=response.getBody();
            logger.debug("id: {} -> findById() request returned with body {}",id,body);
            Staff[] staffs=mapper.readValue(body,Staff[].class);
            if(staffs.length==0){
                logger.debug("id: {} -> findById() had no results",id);
                return Optional.empty();
            }else{
                return Optional.of(staffs[0]);
            }
        }
    }
    
}
