package com.apollocare.backend.Consultationtests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apollocare.backend.models.*;

import com.apollocare.backend.service.ConsultationService;
import com.apollocare.backend.util.SupabaseManager;
import com.fasterxml.jackson.core.JsonProcessingException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TestConsultationService {
    @Mock
	private SupabaseManager manager;

    @Mock
    private PatientRepo patientRepo;

    @Mock
    private ConsultationRepo consultationRepo;

    @Mock
    private DoctorRepo doctorRepo;

    @InjectMocks
    private ConsultationService service;

    private Patient patient;
    private Doctor doctor;
    private Consultation consultation;

    @BeforeEach
    void setup(){
        patient=new Patient("432","joao@emaol.com","Joao Sousa");
        doctor=new Doctor("732","emilio@emaol.com","Emilio ");
        consultation=new Consultation();
        consultation.setPatientId("432");
        consultation.setDoctorId("732");
        consultation.setScheduledDate(1718731562000L);
    }

    @Test
    void testScheduleConsultation() throws JsonProcessingException{

        when(consultationRepo.insert(any())).thenReturn(Optional.of(consultation));
        when(doctorRepo.findById("732")).thenReturn(Optional.of(doctor));
        when(patientRepo.findById("432")).thenReturn(Optional.of(patient));

        Optional<Consultation> response=service.schedule(consultation);
        assertEquals(consultation,response.get());
        
    }

    @Test
    void testFindAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor());
        when(doctorRepo.findAll()).thenReturn(doctors);

        List<Doctor> result = service.findAllDoctors();

        assertEquals(1, result.size());
    }

    @Test
    void testFindAllConsultations() {
        List<Consultation> consultations = new ArrayList<>();
        consultations.add(new Consultation());
        when(consultationRepo.findAll()).thenReturn(consultations);

        List<Consultation> result = service.findAllConsultations();

        assertEquals(1, result.size());
    }

    @Test
    void testGetConsultationById() throws JsonProcessingException {
        consultation.setId(1L);
        when(consultationRepo.findById(1L)).thenReturn(Optional.of(consultation));

        Optional<Consultation> result = service.getConsultationById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testCheckInConsultation() throws JsonProcessingException {
        consultation.setId(1L);
        consultation.setPatientId("432");
        consultation.setDoctorId("732");
        when(consultationRepo.findById(1L)).thenReturn(Optional.of(consultation));

        service.checkInConsultation(1L);

        assertTrue(consultation.getCheckInDate()>0);
        assertEquals("CHECKED_IN", consultation.getState());
    }

    @Test
    void testScheduleConsultation_DoctorNotFound() throws JsonProcessingException {
        when(doctorRepo.findById("999")).thenReturn(Optional.empty());

        consultation.setDoctorId("999");
        Optional<Consultation> response = service.schedule(consultation);
        assertTrue(response.isEmpty());
    }

    @Test
    void testScheduleConsultation_PatientNotFound() throws JsonProcessingException {
        when(patientRepo.findById("999")).thenReturn(Optional.empty());

        consultation.setPatientId("999");
        Optional<Consultation> response = service.schedule(consultation);
        assertTrue(response.isEmpty());
    }

    @Test
    void testCheckInConsultation_ConsultationNotFound() throws JsonProcessingException {
        long id = 123L;
        when(consultationRepo.findById(id)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> service.checkInConsultation(id));
    }

    @Test
    void testFindAllClinics() {
        when(consultationRepo.findAllClinics()).thenReturn(List.of(new Clinic("Location1"), new Clinic("Location2")));
        List<Clinic> result = service.findAllClinics();
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllSpecialties() {
        when(consultationRepo.findAllSpecialties()).thenReturn(List.of(new Specialty("Specialty1"), new Specialty("Specialty2")));
        List<Specialty> result = service.findAllSpecialties();
        assertEquals(2, result.size());
    }

    @Test
    void testFindPatientsByName() {
        String name = "John";
        when(consultationRepo.findPatientByNameLike(any())).thenReturn(List.of(new Patient("1", "mail", "John Doe"), new Patient("2", "mail", "Johnny")));
        List<Patient> result = service.findPatientsByName(name);
        assertEquals(2, result.size());
    }

    @Test
    void testFindConsultationsByPatientId() {
        String id = "123";
        when(consultationRepo.findConsultationsByPatientId(id)).thenReturn(List.of(new Consultation()));
        List<Consultation> result = service.findConsultationsByPatientId(id);
        assertEquals(1, result.size());
    }

    @Test
    void testCheckOutConsultation() throws JsonProcessingException {
        long id = 123L;
        Consultation consultation = new Consultation();
        consultation.setState("CHECKED_IN"); 
        when(consultationRepo.findById(id)).thenReturn(Optional.of(consultation));
    
        service.checkOutConsultation(id);
    
        assertEquals("CHECKED_OUT", consultation.getState());
    }

    
}

