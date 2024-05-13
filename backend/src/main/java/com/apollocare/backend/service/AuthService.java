package com.apollocare.backend.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.apollocare.backend.models.Doctor;
import com.apollocare.backend.models.DoctorRepo;
import com.apollocare.backend.models.Patient;
import com.apollocare.backend.models.PatientRepo;
import com.apollocare.backend.models.Staff;
import com.apollocare.backend.models.StaffRepo;
import com.apollocare.backend.models.User;
import com.apollocare.backend.util.Role;
import com.apollocare.backend.util.SupabaseManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthService {
    private SupabaseManager manager;
    private ObjectMapper mapper;

    public AuthService(SupabaseManager manager){
        this.manager=manager;
        this.mapper=new ObjectMapper();
    }

    private ResponseEntity<String> register(String email,String password) throws JsonProcessingException{
        Map<String,String> body=new HashMap<>();
        
        
        body.put("email",email);
        body.put("password",password);

        return manager.postRequest("auth/v1/signup", mapper.writeValueAsString(body));
    }

    private ResponseEntity<String> login(String email,String password) throws JsonProcessingException{
        Map<String,String> body=new HashMap<>();
        ObjectMapper mapper=new ObjectMapper();
        
        body.put("email",email);
        body.put("password",password);

        return manager.postRequest("auth/v1/token?grant_type=password", mapper.writeValueAsString(body));
    }

    public ResponseEntity<User> register(Role role, String username, String email, String password) throws JsonProcessingException{
        ResponseEntity<String> response=register(email, password);
        if(response.getStatusCode()!=HttpStatus.OK){
            return new ResponseEntity<>(response.getStatusCode());
        }else{
            JsonNode node=mapper.readTree(response.getBody());
            String id=node.get("user").get("id").asText();

            User user=new User(id, email, username);
            switch(role){
                case PATIENT:
                    PatientRepo patientRepo=new PatientRepo(manager);
                    patientRepo.insert((Patient)user);
                    break;
                case DOCTOR:
                    DoctorRepo doctorRepo=new DoctorRepo(manager);
                    doctorRepo.insert((Doctor)user);
                    break;
                case STAFF:
                    StaffRepo staffRepo=new StaffRepo(manager);
                    staffRepo.insert((Staff)user);
                    break;
                default:
                    throw new IllegalArgumentException("Role not accounted for");
            }
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
    }

    public ResponseEntity<User> login(Role role, String email, String password){
        return null;
    }
}
