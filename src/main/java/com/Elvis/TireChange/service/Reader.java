package com.Elvis.TireChange.service;

import com.Elvis.TireChange.model.WorkshopModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This class reads the configuration file where workshop data is stored.
 */
@Service
public class Reader {

    public List<WorkshopModel> readWorkshopConfiguration() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("WorkshopConfiguration.json"), new TypeReference<>() {});
    }
}
