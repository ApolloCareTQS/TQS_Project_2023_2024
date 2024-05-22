package com.apollocare.backend;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


import com.apollocare.backend.models.Specialty;

class TestSpeciality {

    @Test
    public void testNoArgsConstructor() {
        Specialty specialty = new Specialty();
        assertThat(specialty).isNotNull();
        assertThat(specialty.getName()).isNull();
    }

    @Test
    public void testAllArgsConstructor() {
        Specialty specialty = new Specialty("Cardiology");
        assertThat(specialty).isNotNull();
        assertThat(specialty.getName()).isEqualTo("Cardiology");
    }

    @Test
    public void testSettersAndGetters() {
        Specialty specialty = new Specialty();
        specialty.setName("Neurology");
        assertThat(specialty.getName()).isEqualTo("Neurology");
    }

    @Test
    public void testToString() {
        Specialty specialty = new Specialty("Dermatology");
        assertThat(specialty.toString()).isEqualTo("Specialty(name=Dermatology)");
    }

    @Test
    public void testEqualsAndHashCode() {
        Specialty specialty1 = new Specialty("Pediatrics");
        Specialty specialty2 = new Specialty("Pediatrics");
        Specialty specialty3 = new Specialty("Oncology");

        assertThat(specialty1).isEqualTo(specialty2);
        assertThat(specialty1).isNotEqualTo(specialty3);

        assertThat(specialty1.hashCode()).isEqualTo(specialty2.hashCode());
        assertThat(specialty1.hashCode()).isNotEqualTo(specialty3.hashCode());
    }
}
    
