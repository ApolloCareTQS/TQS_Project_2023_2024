package com.apollocare.backend.Consultationtests;

import com.apollocare.backend.models.Clinic;
import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.models.Doctor;
import com.apollocare.backend.models.Patient;
import com.apollocare.backend.models.Specialty;
import com.apollocare.backend.util.SupabaseManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class TestParse {

    @Mock
    private ObjectMapper mapper;

    @Autowired
    private SupabaseManager manager;

    @Test
    void testParseConsultationList_ValidJson_Success() throws IOException {
        String jsonResponse = "[{\"id\":1,\"scheduledDate\":123456789,\"checkInDate\":0,\"receptionDate\":0,\"duration\":30,\"patientId\":\"patientId\",\"doctorId\":\"doctorId\",\"state\":\"state\",\"specialty\":\"specialty\",\"location\":\"location\"}]";

        List<Consultation> expectedConsultations = Collections.singletonList(
                new Consultation(1L, 123456789L, 0L, 0L, 30, "patientId", "doctorId", "state", "specialty",
                        "location"));

        when(mapper.readValue(jsonResponse, new TypeReference<List<Consultation>>() {
        })).thenReturn(expectedConsultations);
        List<Consultation> result = manager.parseConsultationList(jsonResponse);
        assertEquals(expectedConsultations, result);
    }

    @Test
    void testParseDoctorList_ValidJson_Success() throws IOException {
        String jsonResponse = "[{\"id\":\"1\",\"email\":\"drsmith@example.com\",\"name\":\"Dr. Smith\",\"clinic\":\"Clinic A\",\"specialty\":\"Cardiology\"}]";
        List<Doctor> expectedDoctors = Collections
                .singletonList(new Doctor("1", "drsmith@example.com", "Dr. Smith", "Clinic A", "Cardiology"));

        when(mapper.readValue(jsonResponse, new TypeReference<List<Doctor>>() {
        })).thenReturn(expectedDoctors);

        List<Doctor> result = manager.parseDoctorList(jsonResponse);

        assertEquals(expectedDoctors, result);
    }

    @Test
    void testParsePatientList_ValidJson_Success() throws IOException {
        String jsonResponse = "[{\"id\":\"1\",\"email\":\"johndoe@example.com\",\"name\":\"John Doe\"}]";
        List<Patient> expectedPatients = Collections.singletonList(new Patient("1", "johndoe@example.com", "John Doe"));

        when(mapper.readValue(jsonResponse, new TypeReference<List<Patient>>() {
        })).thenReturn(expectedPatients);

        List<Patient> result = manager.parsePatientList(jsonResponse);

        assertEquals(expectedPatients, result);
    }

    @Test
    void testParseClinicList_ValidJson_Success() throws IOException {
        String jsonResponse = "[{\"location\":\"Location A\"}]";
        List<Clinic> expectedClinics = Collections.singletonList(new Clinic("Location A"));

        when(mapper.readValue(jsonResponse, new TypeReference<List<Clinic>>() {
        })).thenReturn(expectedClinics);

        List<Clinic> result = manager.parseClinicList(jsonResponse);

        assertEquals(expectedClinics, result);
    }

    @Test
    void testParseSpecialtyList_ValidJson_Success() throws IOException {
        String jsonResponse = "[{\"name\":\"Cardiology\"}]";
        List<Specialty> expectedSpecialties = Collections.singletonList(new Specialty("Cardiology"));

        when(mapper.readValue(jsonResponse, new TypeReference<List<Specialty>>() {
        })).thenReturn(expectedSpecialties);

        List<Specialty> result = manager.parseSpecialtyList(jsonResponse);

        assertEquals(expectedSpecialties, result);
    }

    @Test
    void testParseDoctorList_InvalidJson_EmptyList() throws Exception {
        String invalidJson = "invalid json";

        when(mapper.readValue(invalidJson, new TypeReference<List<Doctor>>() {})).thenThrow(new RuntimeException());

        List<Doctor> result = manager.parseDoctorList(invalidJson);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testParsePatientList_InvalidJson_EmptyList() throws Exception {
        String invalidJson = "invalid json";

        when(mapper.readValue(invalidJson, new TypeReference<List<Patient>>() {})).thenThrow(new RuntimeException());

        List<Patient> result = manager.parsePatientList(invalidJson);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testParseClinicList_InvalidJson_EmptyList() throws Exception {
        String invalidJson = "invalid json";

        when(mapper.readValue(invalidJson, new TypeReference<List<Clinic>>() {})).thenThrow(new RuntimeException());

        List<Clinic> result = manager.parseClinicList(invalidJson);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testParseSpecialtyList_InvalidJson_EmptyList() throws Exception {
        String invalidJson = "invalid json";

        when(mapper.readValue(invalidJson, new TypeReference<List<Specialty>>() {})).thenThrow(new RuntimeException());

        List<Specialty> result = manager.parseSpecialtyList(invalidJson);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testParseDoctorList_EmptyJson_EmptyList() {
        String emptyJson = "";

        List<Doctor> result = manager.parseDoctorList(emptyJson);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testParsePatientList_EmptyJson_EmptyList() {
        String emptyJson = "";

        List<Patient> result = manager.parsePatientList(emptyJson);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testParseClinicList_EmptyJson_EmptyList() {
        String emptyJson = "";

        List<Clinic> result = manager.parseClinicList(emptyJson);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testParseSpecialtyList_EmptyJson_EmptyList() {
        String emptyJson = "";

        List<Specialty> result = manager.parseSpecialtyList(emptyJson);

        assertEquals(Collections.emptyList(), result);
    }

}
