package com.apollocare.backend.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Staff extends User {
    protected String clinic;

    public Staff(String id,String email,String name, String clinic){
        super(id,email,name);
        this.clinic=clinic;
    }

    public Staff(String id, String email, String name){
        super(id, email, name);
    }
}
