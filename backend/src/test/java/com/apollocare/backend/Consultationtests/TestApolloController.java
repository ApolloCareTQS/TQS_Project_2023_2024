package com.apollocare.backend.Consultationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.apollocare.backend.controller.ApolloController;
import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.models.Doctor;
import com.apollocare.backend.service.ConsultationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ApolloController.class)
class TestApolloController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsultationService consultationService;

    @InjectMocks
    private ApolloController apolloController;

    @Test
    void getAllConsultations_ReturnsListOfConsultations() throws Exception { 
        Consultation consultation = new Consultation();
        consultation.setId(1L);
        List<Consultation> consultations = new ArrayList<>();
        consultations.add(consultation);

        when(consultationService.findAllConsultations()).thenReturn(consultations);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/consultations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getAllDoctors_ReturnsListOfDoctors() throws Exception { 
        Doctor doctor = new Doctor();
        doctor.setId("1");
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);

        when(consultationService.findAllDoctors()).thenReturn(doctors);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/doctors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    void addConsultation_ValidConsultation_ReturnsOk() throws Exception {
        Consultation consultation = new Consultation();
        consultation.setId(1L);

        when(consultationService.schedule(any(Consultation.class))).thenReturn(Optional.of(consultation));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/consultations/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(consultation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void checkInConsultation_ValidId_ReturnsOk() throws Exception {
        Long consultationId = 1L;
  
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/checkin/{id}", consultationId))
                .andExpect(status().isOk())
                .andExpect(content().string("Check-in successful"));
    }

    @Test
    void getConsultationById_ValidId_ReturnsConsultation() throws Exception {
        Consultation consultation = new Consultation();
        consultation.setId(1L);

        when(consultationService.getConsultationById(anyLong())).thenReturn(Optional.of(consultation));
     
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/consultations/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getConsultationById_InvalidId_ReturnsNotFound() throws Exception {
        Long consultationId = 1L;

        when(consultationService.getConsultationById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/consultations/{id}", consultationId))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteConsultation_ValidId_ReturnsNoContent() throws Exception {
        Long consultationId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/delete/{id}", consultationId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAddConsultationCatchBlock() {
        Consultation consultation = new Consultation();
        consultation.setId(1L);
        when(consultationService.schedule(consultation)).thenThrow(new RuntimeException("Test Exception"));

        ResponseEntity<Consultation> response = apolloController.addConsultation(consultation);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}