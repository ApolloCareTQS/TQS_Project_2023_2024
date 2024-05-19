package com.apollocare.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consultation{
    @JsonProperty("id")
    protected Long id;
    @JsonProperty("scheduledDate")
    protected long scheduledDate;
    @JsonProperty("checkInDate")
    protected long checkInDate;
    @JsonProperty("receptionDate")
    protected long receptionDate;
    @JsonProperty("duration")
    protected int duration;
    @JsonProperty("patientId")
    protected String patientId;
    @JsonProperty("doctorId")
    protected String doctorId;
    @JsonProperty("state")
    protected String state;
    @JsonProperty("specialty")
    protected String specialty;
    @JsonProperty("location")
    protected String location;
}
