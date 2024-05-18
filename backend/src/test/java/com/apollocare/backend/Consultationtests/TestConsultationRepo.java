package com.apollocare.backend.Consultationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.models.ConsultationRepo;
import com.apollocare.backend.util.SupabaseManager;
import com.fasterxml.jackson.core.JsonProcessingException;

@ExtendWith(MockitoExtension.class)
class TestConsultationRepo {
    @Mock
    private SupabaseManager manager;

    @InjectMocks
    private ConsultationRepo consultationRepo;

    private Consultation consultation;

    @BeforeEach
    void setup() {
        consultation = new Consultation();
        consultation.setId(1L);
        consultation.setScheduledDate(1718748935000L);
        consultation.setPatientId("abc123");
        consultation.setDoctorId("def456");
        consultation.setSpecialty("cardiology");
        consultation.setLocation(" Lisbon");
    }

    @Test
    void testFindById() throws JsonProcessingException {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(
                "[{\"id\":1,\"scheduledDate\":\" 1718748935000\",\"patientId\":\"abc123\",\"doctorId\":\"def456\",\"specialty\":\"cardiology\",\"location\":\" Lisbon\"}]", HttpStatus.OK));
        assertEquals(Optional.of(consultation), consultationRepo.findById(1L));
    }

    @Test
    void testInsert() throws JsonProcessingException {
        when(manager.postRequest(any(), any())).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
        assertEquals(Optional.of(consultation), consultationRepo.insert(consultation));
    }

    @Test
    void testUpdate() throws JsonProcessingException {
        when(manager.patchRequest(any(), any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertEquals(Optional.of(consultation), consultationRepo.update(consultation));
    }

    @Test
    void testFindByIdErrorCode() throws JsonProcessingException {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS));
        assertEquals(Optional.empty(), consultationRepo.findById(1L));
    }

    @Test
    void testFindByIdNoResults() throws JsonProcessingException {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>("[]", HttpStatus.OK));
        assertEquals(Optional.empty(), consultationRepo.findById(1L));
    }

    @Test
    void testFindAll() throws JsonProcessingException {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(
                "[{\"id\":1,\"scheduledDate\":\" 1718748935000\",\"patientId\":\"abc123\",\"doctorId\":\"def456\",\"specialty\":\"cardiology\",\"location\":\" Lisbon\"}]", HttpStatus.OK));
        List<Consultation> expectedConsultations = Collections.singletonList(consultation);
        when(manager.parseConsultationList(any())).thenReturn(expectedConsultations);
        assertEquals(Collections.singletonList(consultation), consultationRepo.findAll());
    }

    @Test
    void testFindAllErrorCode() throws JsonProcessingException {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS));
        assertEquals(Collections.emptyList(), consultationRepo.findAll());
    }
}
