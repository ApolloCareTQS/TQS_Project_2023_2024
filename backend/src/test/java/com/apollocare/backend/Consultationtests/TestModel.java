package com.apollocare.backend.Consultationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.apollocare.backend.models.Consultation;

public class TestModel {
    @Test
    void testEqualsAndHashCode() {
        Consultation consultation1 = new Consultation(1L, 123456789L, 0L, 0L, 30, "patient1", "doctor1", "state1", "specialty1", "location1");
        Consultation consultation2 = new Consultation(1L, 123456789L, 0L, 0L, 30, "patient1", "doctor1", "state1", "specialty1", "location1");

        assertEquals(consultation1, consultation2);
        assertEquals(consultation1.hashCode(), consultation2.hashCode());

        consultation2.setLocation("location2");

        assertNotEquals(consultation1, consultation2);
        assertNotEquals(consultation1.hashCode(), consultation2.hashCode());
    }

    @Test
    void testAllArgsConstructor() {
        Consultation consultation = new Consultation(1L, 123456789L, 0L, 0L, 30, "patient1", "doctor1", "state1", "specialty1", "location1");

        assertEquals(1L, consultation.getId());
        assertEquals(123456789L, consultation.getScheduledDate());
        assertEquals(0L, consultation.getCheckInDate());
        assertEquals(0L, consultation.getReceptionDate());
        assertEquals(30, consultation.getDuration());
        assertEquals("patient1", consultation.getPatientId());
        assertEquals("doctor1", consultation.getDoctorId());
        assertEquals("state1", consultation.getState());
        assertEquals("specialty1", consultation.getSpecialty());
        assertEquals("location1", consultation.getLocation());
    }
}
