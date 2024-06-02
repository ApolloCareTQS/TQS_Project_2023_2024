package com.apollocare.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @JsonProperty("id")
    @Schema( type = "string", example = "4d44a007-93e7-4041-bc6d-07319fdc4e16")
    protected String id;
    @JsonProperty("email")
    @Schema( type = "string", example = "example@gmail.com")
    protected String email;
    @JsonProperty("name")
    @Schema( type = "string", example = "Bob Alice")
    protected String name;
}
