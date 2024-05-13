package com.apollocare.backend.models;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.apollocare.backend.util.SupabaseManager;

@Component
public class DoctorRepo extends Repository {

    public DoctorRepo(SupabaseManager manager) {
        super(manager);
    }

    public Optional<Doctor> insert(Doctor doctor){
        return null;
    }

    public Optional<Doctor> findById(String id){
        return null;
    }
    
}
