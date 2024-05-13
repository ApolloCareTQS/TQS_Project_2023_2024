package com.apollocare.backend.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Doctor extends Staff{
    protected String specialty;

    public Doctor(String id,String email,String name, String clinic, String specialty){
        super(id,email,name,clinic);
        this.specialty=specialty;
    }

    public Doctor(String id, String email, String name){
        super(id,email,name);
    }
    
}
