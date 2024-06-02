package com.apollocare.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consultation{
    @JsonProperty("id")
    @Schema( type = "Long", example = "1")
    protected Long id;
    @JsonProperty("scheduledDate")
    @Schema( type = "Long", example = "1717284680")
    protected long scheduledDate;
    @JsonProperty("checkInDate")
    @Schema( type = "Long", example = "1717284680")
    protected long checkInDate;
    @JsonProperty("receptionDate")
    @Schema( type = "Long", example = "1717284680")
    protected long receptionDate;
    @JsonProperty("duration")
    @Schema( type = "int", example = "30")
    protected int duration;
    @JsonProperty("patientId")
    @Schema( type = "string", example = "4d44a007-93e7-4041-bc6d-07319fdc4e16")
    protected String patientId;
    @JsonProperty("doctorId")
    @Schema( type = "string", example = "4d44a007-93e7-4041-bc6d-07319fdc4e16")
    protected String doctorId;
    @JsonProperty("state")
    @Schema( type = "string", example = "SCHEDULED")
    protected String state;
    @JsonProperty("specialty")
    @Schema( type = "string", example = "CLINICA GERAL")
    protected String specialty;
    @JsonProperty("location")
    @Schema( type = "string", example = "AVEIRO")
    protected String location;
}
