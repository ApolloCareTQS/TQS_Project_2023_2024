package com.apollocare.backend.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Doctor extends Staff{
    protected String specialty;

    public Doctor(String id,String email,String name, String clinic, String specialty){
        super(id,email,name,clinic);
        this.specialty=specialty;
    }
    
}
