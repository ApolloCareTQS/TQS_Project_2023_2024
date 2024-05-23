package com.apollocare.backend.Consultationtests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import com.apollocare.backend.controller.AdminController;
import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.models.Patient;
import com.apollocare.backend.service.ConsultationService;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsultationService cService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllConsultations() throws Exception {
        when(cService.findAllConsultations()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/admin/v1/all"))
               .andExpect(status().isOk())
               .andExpect(view().name("consultations"))
               .andExpect(model().attributeExists("consultations"));
    }

    @Test
    void testShowAddConsultationForm() throws Exception {
        when(cService.findAllDoctors()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/admin/v1/form"))
               .andExpect(status().isOk())
               .andExpect(view().name("add"))
               .andExpect(model().attributeExists("doctors"))
               .andExpect(model().attributeExists("consultation"));
    }

    @Test
    void testAddConsultation() throws Exception {
        Consultation consultation = new Consultation();
        when(cService.schedule(any(Consultation.class))).thenReturn(Optional.of(consultation));

        mockMvc.perform(post("/admin/v1/add")
                .flashAttr("consultation", consultation))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(consultation.getId()));
    }

    @Test
    void testCheckInConsultation() throws Exception {
        mockMvc.perform(post("/admin/v1/checkin").param("id", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/admin/v1/all"));
        verify(cService).checkInConsultation(1L);
    }

    @Test
    void testGetConsultationById() throws Exception {
        Consultation consultation = new Consultation();
        when(cService.getConsultationById(anyLong())).thenReturn(Optional.of(consultation));

        mockMvc.perform(get("/admin/v1/consultations/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(consultation.getId()));
    }

    @Test
    void testDeleteConsultation() throws Exception {
        mockMvc.perform(delete("/admin/v1/delete/1"))
               .andExpect(status().isNoContent());
        verify(cService).deleteConsultation(1L);
    
    }

    @Test
    void testSearchPatients() throws Exception {
        String searchTerm = "John Doe";
        List<Patient> patients = Collections.singletonList(new Patient());
        when(cService.findPatientsByName(toString())).thenReturn(patients);

        mockMvc.perform(get("/admin/v1/search_patients").param("name", searchTerm))
               .andExpect(status().isOk())
               .andExpect(view().name("patients"))
               .andExpect(model().attributeExists("patients"));
    }

    @Test
    void testCheckOutConsultation() throws Exception {
        mockMvc.perform(post("/admin/v1/checkout").param("id", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/admin/v1/all"));
        verify(cService).checkOutConsultation(1L);
    }

    @Test
    void testGetPatientConsultations() throws Exception {
        String patientId = "patient123";
        List<Consultation> consultations = Collections.singletonList(new Consultation());
        when(cService.findConsultationsByPatientId(patientId)).thenReturn(consultations);

        mockMvc.perform(get("/admin/v1/patient_consultations").param("patientId", patientId))
               .andExpect(status().isOk())
               .andExpect(view().name("consultations"))
               .andExpect(model().attributeExists("consultations"));
    }

    @Test
    void testIndex() throws Exception {
        mockMvc.perform(get("/admin/v1/"))
               .andExpect(status().isOk())
               .andExpect(view().name("index"));
    }

}
