package com.apollocare.backend.models;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.apollocare.backend.util.SupabaseManager;

@Component
public class PatientRepo extends Repository{
    public PatientRepo(SupabaseManager manager) {
        super(manager);
    }

    public Optional<Patient> findById(String id){
        return null;
    }

    public Optional<Patient> insert(Patient user) {
        return null;
    }
}
