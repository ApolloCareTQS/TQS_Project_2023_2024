package com.apollocare.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.apollocare.backend.models.*;
import com.apollocare.backend.util.SupabaseManager;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
class TestRepos {
    @Mock
    private SupabaseManager manager;

    @InjectMocks
    private PatientRepo patientRepo;
    @InjectMocks
    private StaffRepo staffRepo;
    @InjectMocks
    private DoctorRepo doctorRepo;

    private Patient patient;
    private Doctor doctor;
    private Staff staff;

    @BeforeEach
    void setup() {
        patient = new Patient("abc123", "johndoe@email.com", "John Doe");
        doctor = new Doctor("abc123", "johndoe@email.com", "John Doe","apolloClinic","cardiovascular");
        staff = new Staff("abc123", "johndoe@email.com", "John Doe","ApolloClinic");
    }

    @Test
    void testPatientFindById() throws JsonProcessingException {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(
                "[{\"id\":\"abc123\",\"email\":\"johndoe@email.com\",\"name\":\"John Doe\"}]", HttpStatus.OK));
        assertEquals(Optional.of(patient), patientRepo.findById("abc"));
    }

    @Test
    void testStaffFindById() throws JsonProcessingException {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(
                "[{\"id\":\"abc123\",\"email\":\"johndoe@email.com\",\"name\":\"John Doe\",\"clinic\":\"apolloClinic\"}]", HttpStatus.OK));
        assertEquals(Optional.of(staff), staffRepo.findById("abc"));
    }

    @Test
    void testDoctorFindById() throws JsonProcessingException {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(
            "[{\"id\":\"abc123\",\"email\":\"johndoe@email.com\",\"name\":\"John Doe\",\"clinic\":\"apolloClinic\",\"specialty\":\"cardiovascular\"}]", HttpStatus.OK));
        assertEquals(Optional.of(doctor), doctorRepo.findById("abc"));
    }

    @Test
    void testPatientInsertSuccessful() throws JsonProcessingException{
        when(manager.postRequest(any(),any())).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
        assertEquals(Optional.of(patient), patientRepo.insert(patient));
    }

    @Test
    void testDoctorInsertSuccessful() throws JsonProcessingException{
        when(manager.postRequest(any(),any())).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
        assertEquals(Optional.of(doctor), doctorRepo.insert(doctor));
    }

    @Test
    void testStaffInsertSuccessful() throws JsonProcessingException{
        when(manager.postRequest(any(),any())).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
        assertEquals(Optional.of(staff), staffRepo.insert(staff));
    }
}