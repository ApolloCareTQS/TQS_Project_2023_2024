package com.apollocare.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import com.apollocare.backend.models.Doctor;
import com.apollocare.backend.util.SupabaseManager;


import static org.mockito.Mockito.mock;

import java.util.Collections;
import org.mockito.InjectMocks;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class TestSupabase {
    
    @Mock
    private ObjectMapper mapper;

    @Autowired
    private SupabaseManager manager;

    // testing innocuous get/post requests to guarantee the connection is working
    // properly
    @Test
    void testGetRequest() {
        ResponseEntity<String> response = manager.getRequest("rest/v1/Doctor?select=*&id=eq.abc");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("[]",response.getBody());
    }

    @Test
    void testPostRequest(){
        ResponseEntity<String> response = manager.postRequest("auth/v1/token?grant_type=password", "{\"email\":\"test@email.com\",\"password\":\"password\"}");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}



