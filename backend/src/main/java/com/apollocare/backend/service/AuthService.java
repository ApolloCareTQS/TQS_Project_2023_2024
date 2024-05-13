package com.apollocare.backend.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.apollocare.backend.models.*;
import com.apollocare.backend.util.Role;
import com.apollocare.backend.util.SupabaseManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthService {
    private SupabaseManager manager;
    private ObjectMapper mapper;
    private static final Logger logger=LogManager.getLogger();
    private PatientRepo patientRepo;
    private DoctorRepo doctorRepo;
    private StaffRepo staffRepo;

    public AuthService(SupabaseManager manager,PatientRepo patientRepo, DoctorRepo doctorRepo, StaffRepo staffRepo){
        this.manager=manager;
        this.mapper=new ObjectMapper();
        this.patientRepo=patientRepo;
        this.doctorRepo=doctorRepo;
        this.staffRepo=staffRepo;
    }

    private ResponseEntity<String> register(String email,String password) throws JsonProcessingException{
        Map<String,String> body=new HashMap<>();
        
        body.put("email",email);
        body.put("password",password);

        logger.debug("email: {} -> sending signup request",email);
        return manager.postRequest("auth/v1/signup", mapper.writeValueAsString(body));
    }

    private ResponseEntity<String> login(String email,String password) throws JsonProcessingException{
        Map<String,String> body=new HashMap<>();

        body.put("email",email);
        body.put("password",password);

        logger.debug("email: {} -> seding login request",email);
        return manager.postRequest("auth/v1/token?grant_type=password", mapper.writeValueAsString(body));
    }

    public ResponseEntity<User> register(Role role, String username, String email, String password) throws JsonProcessingException{
        ResponseEntity<String> response=register(email, password);
        if(response.getStatusCode()!=HttpStatus.OK){
            logger.debug("email: {} -> signup returned with a status of {}",email,response.getStatusCode());
            return new ResponseEntity<>(response.getStatusCode());
        }else{
            logger.debug("email: {} -> signup response: {}",email,response.getBody());
            JsonNode node=mapper.readTree(response.getBody());
            String id=node.get("user").get("id").asText();

            User user;
            switch(role){
                case PATIENT:
                    Patient patient=new Patient(id,email,username);
                    patientRepo.insert(patient);
                    user=patient;
                    break;
                case DOCTOR:
                    Doctor doctor=new Doctor(id,email,username);
                    doctorRepo.insert(doctor);
                    user=doctor;
                    break;
                case STAFF:
                    Staff staff=new Staff(id,email,username);
                    staffRepo.insert(staff);
                    user=staff;
                    break;
                default:
                    throw new IllegalArgumentException("Role not accounted for");
            }
            logger.debug("email: {} -> signup OK",email);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
    }

    public ResponseEntity<User> login(Role role, String email, String password) throws JsonProcessingException{
        ResponseEntity<String> response=login(email,password);
        if(response.getStatusCode()!=HttpStatus.OK){
            logger.debug("email: {} -> signup returned with a status of {}",email,response.getStatusCode());
            return new ResponseEntity<>(response.getStatusCode());
        }else{
            logger.debug("email: {} -> login response: {}",email,response.getBody());
            JsonNode node=mapper.readTree(response.getBody());
            String id=node.get("user").get("id").asText();

            User user;
            switch(role){
                case PATIENT:
                    Optional<Patient> patient=patientRepo.findById(id);
                    if(patient.isEmpty()){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    user=patient.get();
                    break;
                case DOCTOR:
                    Optional<Doctor> doctor=doctorRepo.findById(id);
                    if(doctor.isEmpty()){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    user=doctor.get();
                    break;
                case STAFF:
                    Optional<Staff> staff=staffRepo.findById(id);
                    if(staff.isEmpty()){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    user=staff.get();
                    break;
                default:
                    throw new IllegalArgumentException("Role not accounted for");
            }
            logger.debug("email: {} -> login OK",email);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
    }
}
