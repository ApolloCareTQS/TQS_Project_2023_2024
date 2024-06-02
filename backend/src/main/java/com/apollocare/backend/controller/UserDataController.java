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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/user")
public class UserDataController {
    private static final Logger logger = LoggerFactory.getLogger(UserDataController.class);

    private final UserDataService service;

    public UserDataController(UserDataService service) {
        this.service = service;
    }

    @Operation(summary = "Get history of patient")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "User is logged in and has access to history",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Consultation.class))}),
        @ApiResponse(responseCode = "401", description = "User isn't logged in",
        content = @Content),
    })
    @GetMapping("/history")
    public ResponseEntity<List<Consultation>> getHistory(@CookieValue(name="token",required=false) String patientId){
        if(patientId==null){
            logger.warn("/history: User not logged in");
            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.UNAUTHORIZED);
        }else{
            return new ResponseEntity<>(service.getConsultationHistory(patientId),HttpStatus.OK);
        }
    }

    @Operation(summary = "Get future appointments of patient")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Accessed future appointments successfully",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Consultation.class))}),
        @ApiResponse(responseCode = "401", description = "User wasn't logged in",
        content = @Content),
    })
    @GetMapping("/scheduled")
    public ResponseEntity<List<Consultation>> getScheduled(@CookieValue(name="token",required=false) String patientId){
        if(patientId==null){
            logger.warn("/scheduled: User not logged in");
            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.UNAUTHORIZED);
        }else{
            return new ResponseEntity<>(service.getConsultationScheduled(patientId),HttpStatus.OK);
        }
    }
}
