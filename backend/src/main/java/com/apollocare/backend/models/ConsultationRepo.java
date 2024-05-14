package com.apollocare.backend.models;

import org.springframework.stereotype.Component;

import com.apollocare.backend.util.SupabaseManager;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ConsultationRepo{
    private SupabaseManager manager;
}
