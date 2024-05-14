package com.apollocare.backend.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Patient extends User {
    public Patient(String id,String email,String name){
        super(id,email,name);
    }
}
