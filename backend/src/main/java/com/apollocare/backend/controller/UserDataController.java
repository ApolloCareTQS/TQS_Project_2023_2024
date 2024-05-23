package com.apollocare.backend.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.service.UserDataService;

@RestController
@RequestMapping("/api/v1/user")
public class UserDataController {
    private static final Logger logger = LoggerFactory.getLogger(UserDataController.class);

    private final UserDataService service;

    public UserDataController(UserDataService service) {
        this.service = service;
    }

    @GetMapping("/history")
    public ResponseEntity<List<Consultation>> getHistory(@CookieValue(name="token",required=false) String patientId){
        if(patientId==null){
            logger.warn("/history: User not logged in");
            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.UNAUTHORIZED);
        }else{
            return new ResponseEntity<>(service.getConsultationHistory(patientId),HttpStatus.OK);
        }
    }
}
