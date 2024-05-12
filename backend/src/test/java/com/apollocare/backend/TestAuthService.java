package com.apollocare.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.apollocare.backend.models.Patient;
import com.apollocare.backend.models.PatientRepo;
import com.apollocare.backend.models.User;
import com.apollocare.backend.service.AuthService;
import com.apollocare.backend.util.SupabaseManager;
import com.fasterxml.jackson.core.JsonProcessingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import static com.apollocare.backend.util.Role.*;

@SpringBootTest
class TestAuthService {
	@Mock
	private SupabaseManager manager;

	@Mock
	private PatientRepo repo;

	@InjectMocks
	private AuthService service;

	private Patient patient;

	@BeforeEach
	void setup(){
		patient=new Patient("123abc","test2@email.com","test2");
	}

	@Test
	void testSignUpAvailablePatient() throws JsonProcessingException{
		when(manager.postRequest(any(), any())).thenReturn(new ResponseEntity<String>("\"user\":{\"id\":\"123abc\"}",HttpStatus.OK));
		when(repo.getFromId("123abc")).thenReturn(Optional.of(patient));
		ResponseEntity<User> response=service.register(PATIENT,"test2","test2@email.com","password");
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertEquals(patient, response.getBody());
	}

	@Test
	void testSignUpTakenPatient() throws JsonProcessingException{
		when(manager.postRequest(any(), any())).thenReturn(new ResponseEntity<String>("error message",HttpStatus.UNPROCESSABLE_ENTITY));
		ResponseEntity<User> response=service.register(PATIENT,"test2@email.com","test2","password");
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test 
	void testLogInSuccessPatient(){
		when(manager.postRequest(any(), any())).thenReturn(new ResponseEntity<String>("\"user\":{\"id\":\"123abc\"}",HttpStatus.OK));
		when(repo.getFromId("123abc")).thenReturn(Optional.of(patient));
		ResponseEntity<User> response=service.login(PATIENT, "test2@email.com","password");
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertEquals(patient, response.getBody());
	}

	@Test
	void testLogInWrongCredentialsPatient() throws JsonProcessingException{
		when(manager.postRequest(any(), any())).thenReturn(new ResponseEntity<String>("error message",HttpStatus.UNPROCESSABLE_ENTITY));
		ResponseEntity<User> response=service.login(PATIENT,"test2@email.com","password");
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertNull(response.getBody());
	}

}
