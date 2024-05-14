package com.apollocare.backend.util;

//this classes contains various classes to be used as request bodies

public record SignUpRequest(String role,String username,String email, String password){}