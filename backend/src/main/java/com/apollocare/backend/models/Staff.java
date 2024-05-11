package com.apollocare.backend.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Staff extends User {
    private String clinic;

    public Staff(String id,String email,String name, String clinic){
        super(id,email,name);
        this.clinic=clinic;
    }

    public Staff(String json){
        super(json);
    }
}
