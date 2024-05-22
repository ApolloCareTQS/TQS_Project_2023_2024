package com.apollocare.backend;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


import com.apollocare.backend.models.Clinic;

class TestClinic {

     @Test
    void testNoArgsConstructor() {
        Clinic clinic = new Clinic();
        assertThat(clinic).isNotNull();
        assertThat(clinic.getLocation()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        Clinic clinic = new Clinic("AVEIRO");
        assertThat(clinic).isNotNull();
        assertThat(clinic.getLocation()).isEqualTo("AVEIRO");
    }

    @Test
    void testSettersAndGetters() {
        Clinic clinic = new Clinic();
        clinic.setLocation("AVEIRO");
        assertThat(clinic.getLocation()).isEqualTo("AVEIRO");
    }

    @Test
    void testToString() {
        Clinic clinic = new Clinic("AVEIRO");
        assertThat(clinic.toString()).hasToString("Clinic(location=AVEIRO)");
    }

    @Test
    void testEqualsAndHashCode() {
        Clinic clinic1 = new Clinic("AVEIRO");
        Clinic clinic2 = new Clinic("AVEIRO");
        Clinic clinic3 = new Clinic("PORTO");

        assertThat(clinic1).isEqualTo(clinic2);
        assertThat(clinic1).isNotEqualTo(clinic3);

        assertThat(clinic1.hashCode()).isEqualTo(clinic2.hashCode());
        assertThat(clinic1.hashCode()).isNotEqualTo(clinic3.hashCode());
    }
}
    
