package com.apollocare.backend.models;

import java.util.Optional;

import com.apollocare.backend.util.SupabaseManager;

public class StaffRepo extends Repository {

    public StaffRepo(SupabaseManager manager) {
        super(manager);
    }

    public Optional<Staff> insert(Staff staff){
        return null;
    }
    
}
