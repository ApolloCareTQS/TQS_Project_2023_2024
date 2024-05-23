package com.apollocare.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.models.ConsultationRepo;
import com.apollocare.backend.service.UserDataService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static com.apollocare.backend.util.State.*;


@ExtendWith(MockitoExtension.class)
class TestUserDataService {
    @Mock
    private ConsultationRepo consultationRepo;

    @InjectMocks
    private UserDataService service;

    private Consultation consultation1;
    private Consultation consultation2;
    private Consultation consultation3;

    @BeforeEach
    void setup(){
        consultation1=new Consultation();
        consultation1.setId(1L);
        consultation1.setPatientId("123abc");
        consultation1.setState(CHECKED_OUT.name());

        consultation2=new Consultation();
        consultation2.setId(2L);
        consultation2.setPatientId("123abc");
        consultation2.setState(CHECKED_OUT.name());

        consultation3=new Consultation();
        consultation3.setId(3L);
        consultation3.setPatientId("123abc");
        consultation3.setState(SCHEDULED.name());
    }

    @Test
    void testGetHistory(){
        List<Consultation> consultationList=new ArrayList<>();
        consultationList.add(consultation1);
        consultationList.add(consultation2);

        when(consultationRepo.findAllByPatientIDAndState("123abc",CHECKED_OUT)).thenReturn(consultationList);
        List<Consultation> result=service.getConsultationHistory("123abc");
        assertEquals(2, result.size());
    }

    @Test
    void testGetScheduled(){
        List<Consultation> consultationList=new ArrayList<>();
        consultationList.add(consultation3);

        when(consultationRepo.findAllByPatientIDAndState("123abc",SCHEDULED)).thenReturn(consultationList);
        List<Consultation> result=service.getConsultationScheduled("123abc");
        assertEquals(1, result.size());
    }
}
