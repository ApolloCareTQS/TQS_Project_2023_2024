package com.apollocare.backend.models;

import com.apollocare.backend.util.SupabaseManager;
public class Repository {
    protected SupabaseManager manager;

    public Repository(SupabaseManager manager){
        this.manager=manager;
    }
}
