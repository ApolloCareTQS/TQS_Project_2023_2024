package com.apollocare.backend.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.models.User;
import com.apollocare.backend.service.AuthService;
import com.apollocare.backend.util.LogInRequest;
import com.apollocare.backend.util.Role;
import com.apollocare.backend.util.SignUpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {
    private final AuthService service;
        private static final Logger logger=LogManager.getLogger();

    public AuthController(AuthService service){
        this.service=service;
    }

    //in order to get the tokens, use this:
    @Operation(summary = "Logout from service")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Logged out successfully",
        content = @Content),
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue(name="token",required=false) String token, @CookieValue(name="role",required = false) String role, HttpServletResponse response){
        if(token!=null || role!=null){
            //there's no explicit "deleteCookie", so we instead override it with a null cookie with a Max-Age of 0
            Cookie tokenCookie=generateCookie("token", null);
            tokenCookie.setMaxAge(0);
            Cookie roleCookie=generateCookie("role", null);
            roleCookie.setMaxAge(0);

            response.addCookie(tokenCookie);
            response.addCookie(roleCookie);
        }
        return new ResponseEntity<>("Logged out successfully",HttpStatus.OK);
    }

    @Operation(summary = "Register new user")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Registered successfully",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid user role",
        content = @Content),
        @ApiResponse(responseCode = "500", description = "Unmarshaling error",
        content = @Content),
    })
    //TODO: find a way to prevent unauthorized users from signing up as doctor/staff (if implementing role-based access restrictions)
    @PostMapping("/register")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest requestBody, HttpServletResponse response){
        String username=requestBody.username();
        String email=requestBody.email();
        String password=requestBody.password();
        Role role;
        try{
            role=Role.valueOf(requestBody.role());
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<User> user;
        try {
            logger.debug("email: {} -> registering",email);
            user = service.register(role, username, email, password);
        } catch (JsonProcessingException e) {
            logger.warn("email: {} -> unmarshaling error",email);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(user.getStatusCode()==HttpStatus.OK){
            String id=user.getBody().getId();
            response.addCookie(generateCookie("token", id));
            response.addCookie(generateCookie("role", role.name()));
            logger.debug("email: {} -> cookies set",email);
        }
        return user;
    }

    @Operation(summary = "User login")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Logged in successfully",
        content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid user role",
        content = @Content),
        @ApiResponse(responseCode = "500", description = "Unmarshaling error",
        content = @Content),
    })
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LogInRequest requestBody, HttpServletResponse response){
        String email=requestBody.email();
        String password=requestBody.password();
        Role role;
        try{
            role=Role.valueOf(requestBody.role());
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<User> user;
        try {
            logger.debug("email: {} -> logging in",email);
            user = service.login(role, email, password);
        } catch (JsonProcessingException e) {
            logger.warn("email: {} -> unmarshaling error",email);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(user.getStatusCode()==HttpStatus.OK){
            String id=user.getBody().getId();
            response.addCookie(generateCookie("token", id));
            response.addCookie(generateCookie("role", role.name()));
            logger.debug("email: {} -> cookies set",email);
        }
        return user;
    }

    private Cookie generateCookie(String key,String value){
        Cookie cookie=new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        return cookie;
    }
}
