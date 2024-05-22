package com.apollocare.backend;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


import com.apollocare.backend.models.Specialty;

class TestSpeciality {

    @Test
    void testNoArgsConstructor() {
        Specialty specialty = new Specialty();
        assertThat(specialty).isNotNull();
        assertThat(specialty.getName()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        Specialty specialty = new Specialty("Cardiology");
        assertThat(specialty).isNotNull();
        assertThat(specialty.getName()).isEqualTo("Cardiology");
    }

    @Test
    void testSettersAndGetters() {
        Specialty specialty = new Specialty();
        specialty.setName("Neurology");
        assertThat(specialty.getName()).isEqualTo("Neurology");
    }

    @Test
    void testToString() {
        Specialty specialty = new Specialty("Dermatology");
        assertThat(specialty).hasToString("Specialty(name=Dermatology)");
    }

    @Test
    void testEqualsAndHashCode() {
        Specialty specialty1 = new Specialty("Pediatrics");
        Specialty specialty2 = new Specialty("Pediatrics");
        Specialty specialty3 = new Specialty("Oncology");

        assertThat(specialty1)
            .isEqualTo(specialty2)
            .isNotEqualTo(specialty3);

        assertThat(specialty1.hashCode())
            .hasSameHashCodeAs(specialty2)
            .isNotEqualTo(specialty3.hashCode());
    }
}
    
