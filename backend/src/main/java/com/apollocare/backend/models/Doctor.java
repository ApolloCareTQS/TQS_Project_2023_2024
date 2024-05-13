package com.apollocare.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Doctor extends Staff{
    @JsonProperty("specialty")
    protected String specialty;

    public Doctor(String id,String email,String name, String clinic, String specialty){
        super(id,email,name,clinic);
        this.specialty=specialty;
    }

    public Doctor(String id, String email, String name){
        super(id,email,name);
    }
    
}
