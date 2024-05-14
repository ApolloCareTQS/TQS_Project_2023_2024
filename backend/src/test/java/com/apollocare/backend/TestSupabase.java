package com.apollocare.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.apollocare.backend.util.SupabaseManager;

@SpringBootTest
public class TestSupabase {
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
