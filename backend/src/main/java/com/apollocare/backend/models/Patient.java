package com.apollocare.backend.models;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public class Patient extends User {
    public Patient(String id,String email,String name){
        super(id,email,name);
    }

    public Patient(String json){
        super(json);
    }
}
