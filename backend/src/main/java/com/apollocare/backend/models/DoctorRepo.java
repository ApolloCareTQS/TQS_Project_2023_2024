package com.apollocare.backend.models;

import java.util.Optional;

import com.apollocare.backend.util.SupabaseManager;

public class DoctorRepo extends Repository {

    public DoctorRepo(SupabaseManager manager) {
        super(manager);
    }

    public Optional<Doctor> insert(Doctor doctor){
        return null;
    }
    
}
