package com.apollocare.backend.models;

import java.util.Optional;

import com.apollocare.backend.util.SupabaseManager;

public class PatientRepo extends Repository{
    public PatientRepo(SupabaseManager manager) {
        super(manager);
    }

    public Optional<Patient> getFromId(String id){
        return null;
    }
}
