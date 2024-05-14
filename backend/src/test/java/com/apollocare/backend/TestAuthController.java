package com.apollocare.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.apollocare.backend.models.User;
import com.apollocare.backend.service.AuthService;
import com.apollocare.backend.util.LogInRequest;
import com.apollocare.backend.util.SignUpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.module.mockmvc.response.MockMvcResponse;

import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.apollocare.backend.util.Role.*;

@SpringBootTest
@AutoConfigureMockMvc
class TestAuthController {
    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthService service;

    private User user;
    private LogInRequest logInRequest;
    private SignUpRequest signUpRequest;

    @BeforeEach
    void setup() {
        user = new User("123abc", "johndoe@email.com", "John Doe");
        logInRequest = new LogInRequest("PATIENT","johndoe@email.com", "password");
        signUpRequest = new SignUpRequest("PATIENT", "John Doe", "johndoe@email.com", "password");
    }

    @Test
    void testSignUpAvailablePatient() throws JsonProcessingException {
        when(service.register(PATIENT, "John Doe", "johndoe@email.com", "password")).thenReturn(new ResponseEntity<>(user,HttpStatus.OK));
        MockMvcResponse response = given()
                .mockMvc(mock)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(signUpRequest))
                .when()
                .post("/auth/v1/register");

        response.then().statusCode(200);
        
        assertEquals(mapper.writeValueAsString(user),response.asString());
        
        Map<String,String> cookies=response.getCookies();

        assertTrue(cookies.containsKey("token"));
        assertTrue(cookies.containsKey("role"));
        assertEquals("123abc", cookies.get("token"));
        assertEquals("PATIENT", cookies.get("role"));
    }

    @Test
    void testSignUpTakenPatient() throws JsonProcessingException {
        when(service.register(PATIENT, "John Doe", "johndoe@email.com", "password")).thenReturn(new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));

        MockMvcResponse response = given()
                .mockMvc(mock)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(signUpRequest))
                .when()
                .post("/auth/v1/register");

        response.then().statusCode(422);
        
        Map<String,String> cookies=response.getCookies();

        assertFalse(cookies.containsKey("token"));
        assertFalse(cookies.containsKey("role"));
    }

    @Test
    void testLogInSuccessPatient() throws JsonProcessingException {
        when(service.login(PATIENT, "johndoe@email.com", "password")).thenReturn(new ResponseEntity<>(user,HttpStatus.OK));

        MockMvcResponse response = given()
                .mockMvc(mock)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(logInRequest))
                .when()
                .post("/auth/v1/login");

        response.then().statusCode(200);
        
        assertEquals(mapper.writeValueAsString(user),response.asString());
        
        Map<String,String> cookies=response.getCookies();

        assertTrue(cookies.containsKey("token"));
        assertTrue(cookies.containsKey("role"));
        assertEquals("123abc", cookies.get("token"));
        assertEquals("PATIENT", cookies.get("role"));
    }

    @Test
    void testLogInWrongCredentialsPatient() throws JsonProcessingException{
        when(service.login(PATIENT, "johndoe@email.com", "password")).thenReturn(new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
        
        MockMvcResponse response = given()
                .mockMvc(mock)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(logInRequest))
                .when()
                .post("/auth/v1/login");

        response.then().statusCode(422);

        Map<String,String> cookies=response.getCookies();
        assertFalse(cookies.containsKey("token"));
        assertFalse(cookies.containsKey("role"));        
    }
}
