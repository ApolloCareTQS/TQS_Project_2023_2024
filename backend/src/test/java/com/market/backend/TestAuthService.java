package com.market.backend;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.apollocare.backend.models.Patient;
import com.apollocare.backend.models.PatientRepo;
import com.apollocare.backend.service.AuthService;
import com.apollocare.backend.util.SupabaseManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

@SpringBootTest
class TestAuthService {
	@Mock
	private SupabaseManager manager;

	@Mock
	private PatientRepo repo;

	@InjectMocks
	private AuthService service;

	@Test
	void testSignUpAvailable(){
		Patient patient=new Patient("123abc","test");
		when(manager.postRequest(any(), any())).thenReturn(new ResponseEntity(,HttpStatus.UNPROCESSABLE_ENTITY));
		when(repo.getFromId("123abc")).thenReturn(Optional.of(patient));
		ResponseEntity<Patient> response=service.register("test2@email.com","password");
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertEquals(patient, response.getBody());
	}

	@Test
	void testSignUpTaken(){
		when(manager.postRequest(any(), any())).thenReturn(new ResponseEntity("error message",HttpStatus.UNPROCESSABLE_ENTITY));
		ResponseEntity<Patient> response=service.register("test2@email.com","password");
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test testLogInSuccess(){
	}

	@Test
	testLogInWrongCredentials(){
	}
}
