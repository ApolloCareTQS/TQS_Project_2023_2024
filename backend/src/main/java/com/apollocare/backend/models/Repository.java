package com.apollocare.backend.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.apollocare.backend.util.SupabaseManager;
import com.fasterxml.jackson.databind.ObjectMapper;
public class Repository {
    protected SupabaseManager manager;
    protected ObjectMapper mapper;
    protected static final Logger logger=LogManager.getLogger();

    public Repository(SupabaseManager manager){
        this.manager=manager;
        this.mapper=new ObjectMapper();
    }
}
