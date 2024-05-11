package com.apollocare.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient implements Entity {
    private String id;
    private String name;
    
    public Patient(String json){}
}
