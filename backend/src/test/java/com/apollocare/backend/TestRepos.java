package com.apollocare.backend;

import static com.apollocare.backend.util.State.CHECKED_OUT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.apollocare.backend.models.*;
import com.apollocare.backend.util.SupabaseManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class TestRepos {
    @Mock
    private SupabaseManager manager;

    @InjectMocks
    private PatientRepo patientRepo;
    @InjectMocks
    private StaffRepo staffRepo;
    @InjectMocks
    private DoctorRepo doctorRepo;
    @InjectMocks
    private ConsultationRepo consultationRepo;

    private Patient patient;
    private Doctor doctor;
    private Staff staff;

    @BeforeEach
    void setup() {
        patient = new Patient("abc123", "johndoe@email.com", "John Doe");
        doctor = new Doctor("abc123", "johndoe@email.com", "John Doe","apolloClinic","cardiovascular");
        staff = new Staff("abc123", "johndoe@email.com", "John Doe","apolloClinic");
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

    @Test
    void testFindByIdErrorCode() throws JsonProcessingException{
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS));
        assertEquals(Optional.empty(), patientRepo.findById("abc123"));
        assertEquals(Optional.empty(), staffRepo.findById("abc123"));
        assertEquals(Optional.empty(), doctorRepo.findById("abc123"));
    }
    
    @Test
    void testStaffFindByIdNoResults() throws JsonProcessingException{
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>("[]",HttpStatus.OK));
        assertEquals(Optional.empty(), patientRepo.findById("abc123"));
        assertEquals(Optional.empty(), staffRepo.findById("abc123"));
        assertEquals(Optional.empty(), doctorRepo.findById("abc123"));
    }

    @Test
    void testDoctorFindAll() {
        String jsonResponse = "[{\"id\":\"abc123\",\"email\":\"johndoe@email.com\",\"name\":\"John Doe\",\"clinic\":\"apolloClinic\",\"specialty\":\"cardiovascular\"}]";
        when(manager.getRequest("/rest/v1/Doctor?select=*")).thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));
        when(manager.parseDoctorList(jsonResponse)).thenReturn(Arrays.asList(doctor));

        List<Doctor> expectedDoctors = Arrays.asList(doctor);
        assertEquals(expectedDoctors, doctorRepo.findAll());
    }

    @Test
    void testDoctorFindAllEmptyList() {
        when(manager.getRequest("/rest/v1/Doctor?select=*")).thenReturn(new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR));
        List<Doctor> expectedDoctors = Collections.emptyList();
        assertEquals(expectedDoctors, doctorRepo.findAll());
    }

    @Test
    void testConsultationFindAllByPatientIdAndState() throws JsonProcessingException{
        Consultation consultation1=new Consultation();
        consultation1.setId(1L);
        consultation1.setPatientId("123abc");
        consultation1.setState(CHECKED_OUT.name());

        Consultation consultation2=new Consultation();
        consultation2.setId(2L);
        consultation2.setPatientId("123abc");
        consultation2.setState(CHECKED_OUT.name());

        List<Consultation> consultations=new ArrayList<>();
        consultations.add(consultation1);
        consultations.add(consultation2);

        ObjectMapper mapper=new ObjectMapper();
        when(manager.getRequest("/rest/v1/Consultation?and=(patientId.eq.123abc,state.eq.CHECKED_OUT)&select=*")).thenReturn(new ResponseEntity<>(mapper.writeValueAsString(consultations), HttpStatus.OK));
        assertEquals(Collections.emptyList(), consultationRepo.findAllByPatientIDAndState("123abc", CHECKED_OUT));
    }

    @Test
    void testConsultationFindAllByPatientIdAndStateEmptyList(){
        when(manager.getRequest("/rest/v1/Consultation?and=(patientId.eq.123abc,state.eq.CHECKED_OUT)&select=*")).thenReturn(new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR));
        assertEquals(Collections.emptyList(), consultationRepo.findAllByPatientIDAndState("123abc", CHECKED_OUT));
    }
    
}