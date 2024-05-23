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

import com.apollocare.backend.models.Clinic;
import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.models.ConsultationRepo;
import com.apollocare.backend.models.Patient;
import com.apollocare.backend.models.Specialty;
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
        consultation.setSpecialty("ORTOPEDIA");
        consultation.setLocation("LISBOA");
    }

    @Test
    void testFindById() throws JsonProcessingException {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(
                "[{\"id\":1,\"scheduledDate\":\" 1718748935000\",\"patientId\":\"abc123\",\"doctorId\":\"def456\",\"specialty\":\"ORTOPEDIA\",\"location\":\"LISBOA\"}]",
                HttpStatus.OK));
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
    void testFindAll() {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(
                "[{\"id\":1,\"scheduledDate\":\" 1718748935000\",\"patientId\":\"abc123\",\"doctorId\":\"def456\",\"specialty\":\"cardiology\",\"location\":\" Lisbon\"}]",
                HttpStatus.OK));
        List<Consultation> expectedConsultations = Collections.singletonList(consultation);
        when(manager.parseConsultationList(any())).thenReturn(expectedConsultations);
        assertEquals(Collections.singletonList(consultation), consultationRepo.findAll());
    }

    @Test
    void testFindAllErrorCode() {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS));
        assertEquals(Collections.emptyList(), consultationRepo.findAll());
    }

    @Test
    void testFindAllClinics() {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(
                "[{\"location\":\"Location1\"},{\"location\":\"Location2\"}]", HttpStatus.OK));
        List<Clinic> expectedClinics = List.of(new Clinic("Location1"), new Clinic("Location2"));

        when(manager.parseClinicList(any())).thenReturn(expectedClinics);

        assertEquals(expectedClinics, consultationRepo.findAllClinics());
    }

    @Test
    void testFindAllClinicsErrorCode() {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        assertEquals(Collections.emptyList(), consultationRepo.findAllClinics());
    }

    @Test
    void testFindAllSpecialties() {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(
                "[{\"name\":\"Specialty1\"},{\"name\":\"Specialty2\"}]", HttpStatus.OK));

        List<Specialty> expectedSpecialties = List.of(new Specialty("Specialty1"), new Specialty("Specialty2"));

        when(manager.parseSpecialtyList(any())).thenReturn(expectedSpecialties);
        assertEquals(expectedSpecialties, consultationRepo.findAllSpecialties());
    }

    @Test
    void testFindAllSpecialtiesErrorCode() {
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        assertEquals(Collections.emptyList(), consultationRepo.findAllSpecialties());
    }

    @Test
    void testFindPatientByNameLike() {
        String name = "John";
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(
                "[{\"id\":\"1\",\"email\":\"john@example.com\",\"name\":\"John Doe\"},{\"id\":\"2\",\"email\":\"johnny@example.com\",\"name\":\"Johnny\"}]", HttpStatus.OK));
        
        List<Patient> expectedPatients = List.of(
                new Patient("1", "john@example.com", "John Doe"),
                new Patient("2", "johnny@example.com", "Johnny")
        );
        when(manager.parsePatientList(any())).thenReturn(expectedPatients);
        assertEquals(expectedPatients, consultationRepo.findPatientByNameLike(name));
    }
    

    @Test
    void testFindPatientByNameLikeErrorCode() {
        String name = "John";
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        assertEquals(Collections.emptyList(), consultationRepo.findPatientByNameLike(name));
    }

    @Test
    void testFindConsultationsByPatientId() {
        // Defina o ID do paciente para a pesquisa
        String patientId = "123";
    
        // Simule a resposta do SupabaseManager com duas consultas correspondentes ao ID do paciente
        when(manager.getRequest(any())).thenReturn(new ResponseEntity<>(
                "[{\"id\":1,\"scheduledDate\":1718748935000,\"checkInDate\":0,\"receptionDate\":0,\"duration\":0,\"patientId\":\"123\",\"doctorId\":\"456\",\"state\":\"\",\"specialty\":\"Cardiology\",\"location\":\"Lisbon\"}," +
                        "{\"id\":2,\"scheduledDate\":1718748935000,\"checkInDate\":0,\"receptionDate\":0,\"duration\":0,\"patientId\":\"123\",\"doctorId\":\"789\",\"state\":\"\",\"specialty\":\"Orthopedics\",\"location\":\"Madrid\"}]",
                HttpStatus.OK));
    
        // Crie uma lista esperada de consultas com base na resposta simulada
        List<Consultation> expectedConsultations = List.of(
                new Consultation(1L, 1718748935000L, 0L, 0L, 0, "123", "456", "", "Cardiology", "Lisbon"),
                new Consultation(2L, 1718748935000L, 0L, 0L, 0, "123", "789", "", "Orthopedics", "Madrid")
        );
    
        // Simule a chamada do método parseConsultationList()
        when(manager.parseConsultationList(any())).thenReturn(expectedConsultations);
    
        // Teste se o método findConsultationsByPatientId() retorna a lista esperada de consultas
        assertEquals(expectedConsultations, consultationRepo.findConsultationsByPatientId(patientId));
    }


}
