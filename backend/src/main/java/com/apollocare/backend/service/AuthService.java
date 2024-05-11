package com.apollocare.backend.service;

import org.springframework.http.ResponseEntity;

import com.apollocare.backend.models.Patient;

public class AuthService {
    public ResponseEntity<Patient> register(String username, String password);
    
    public ResponseEntity<Patient> login(String username, String password);
}
