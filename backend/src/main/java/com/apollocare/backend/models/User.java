package com.apollocare.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    protected String id;
    protected String email;
    protected String name;
    
    public User(String json){
        //TODO: Add conversion from json
    }
}
