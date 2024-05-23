package com.apollocare.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.service.UserDataService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.apollocare.backend.util.State.*;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUserDataController {
    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserDataService service;

    private Consultation consultation1;
    private Consultation consultation2;
    private Consultation consultation3;

    private List<Consultation> historyList;

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

        historyList=new ArrayList<>();
        historyList.add(consultation1);
        historyList.add(consultation2);


    }

    @Test
    void testGetHistory(){
        when(service.getConsultationHistory("123abc")).thenReturn(historyList);
        given()
            .mockMvc(mock)
            .cookie("token","123abc")
        .when()
            .get("/api/v1/user/history")
        .then()
            .statusCode(200)
            .body("$",Matchers.hasSize(2));
    }

    @Test
    void testGetHistoryNoToken(){
        when(service.getConsultationHistory("123abc")).thenReturn(historyList);
        given()
            .mockMvc(mock)
        .when()
            .get("/api/v1/user/history")
        .then()
            .statusCode(401)
            .body("$",Matchers.hasSize(0));   
    }
}

