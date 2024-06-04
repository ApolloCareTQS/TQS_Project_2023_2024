package com.apollocare.backend.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.models.Doctor;
import com.apollocare.backend.service.ConsultationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1")
public class ApolloController {

    private static final Logger logger = LoggerFactory.getLogger(ApolloController.class);


    private final ConsultationService cService;

    public ApolloController(ConsultationService cService) {
        this.cService = cService;
    }

    @Operation(summary = "Get all scheduled consultations")
    @ApiResponse(responseCode = "200", description = "Got all consultations successfully", content = @Content)
    @GetMapping("/consultations")
    public ResponseEntity<List<Consultation>> getAllConsultations() {
        List<Consultation> consultations = cService.findAllConsultations();
        return ResponseEntity.ok(consultations);
    }

    @Operation(summary = "Get all doctors registred in the system")
    @ApiResponse(responseCode = "200", description = "Got all doctors successfully", content = @Content)
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getDoctors() {
        List<Doctor> doctors = cService.findAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @Operation(summary = "Add a new consultation")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Added new consultation successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Consultation.class))), 
        @ApiResponse(responseCode = "500", description = "Something wrong happened while adding a new consultation",
        content = @Content)
    })
    @PostMapping("/consultations/add")
    public ResponseEntity<Consultation> addConsultation(@RequestBody Consultation consultation) {
        try {
            Optional<Consultation> optionalConsultation = cService.schedule(consultation);
            return optionalConsultation.map(ResponseEntity::ok)
                                       .orElseGet(() -> {
                                           logger.error("Failed to schedule consultation");
                                           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                                       });
        } catch (Exception e) {
            logger.error("An error occurred while adding the consultation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Check-in consultation by id")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Check-in successfully",
        content = @Content), 
        @ApiResponse(responseCode = "500", description = "Check-in failed",
        content = @Content)
    })
    @PostMapping("/checkin/{id}")
    public ResponseEntity<String> checkInConsultation(@PathVariable("id") Long id) {
        try {
            cService.checkInConsultation(id);
            return ResponseEntity.ok("Check-in successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Check-in failed");
        }
    }

    @Operation(summary = "Get consultation by id")
    @ApiResponse(responseCode = "200", description = "Consultation found", 
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Consultation.class)))
    @GetMapping("/consultations/{id}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable Long id) {
        Optional<Consultation> optionalConsultation = cService.getConsultationById(id);
        return optionalConsultation.map(ResponseEntity::ok)
                                   .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete consultation by id")
    @ApiResponse(responseCode = "200", description = "Consultation deleted successfully", content = @Content)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
        cService.deleteConsultation(id);
        return ResponseEntity.noContent().build();
    }

}
