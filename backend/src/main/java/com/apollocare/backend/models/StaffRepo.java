package com.apollocare.backend.models;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.apollocare.backend.util.SupabaseManager;

@Component
public class StaffRepo extends Repository {

    public StaffRepo(SupabaseManager manager) {
        super(manager);
    }

    public Optional<Staff> insert(Staff staff){
        return null;
    }

    public Optional<Staff> findById(String id){
        return null;
    }
    
}
