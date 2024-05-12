package com.apollocare.backend.service;

import org.springframework.http.ResponseEntity;

import com.apollocare.backend.models.User;
import com.apollocare.backend.util.Role;

public class AuthService {
    public String register(String email,String password){
        return "";
    }
    public String login(String email,String password){
        return "";
    }

    public ResponseEntity<User> register(Role role, String username, String email, String password){
        return null;
    }
    public ResponseEntity<User> login(Role role, String email, String password){
        return null;
    }
}
