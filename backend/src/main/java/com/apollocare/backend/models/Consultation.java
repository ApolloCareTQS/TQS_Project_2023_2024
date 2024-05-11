package com.apollocare.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consultation{
    private long id;
    private long scheduledDate;
    private long checkInDate;
    private long receptionDate;
    private int duration;
    private Patient patient;
    private Doctor doctor;
    private String state;
    private String specialty;
    private String location;

    public Consultation(String json){}
}
