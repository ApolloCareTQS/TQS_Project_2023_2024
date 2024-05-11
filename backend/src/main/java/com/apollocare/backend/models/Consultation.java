package com.apollocare.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consultation implements Entity{
    private long id;
    private long scheduledDate;
    private long checkInDate;
    private long receptionDate;
    private int duration;
    private long patientId;
    private long doctorId;
    private String state;
    private String specialty;
    private String location;
}
