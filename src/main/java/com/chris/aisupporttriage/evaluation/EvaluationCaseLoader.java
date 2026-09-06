package com.chris.aisupporttriage.evaluation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class EvaluationCaseLoader {
    private final ObjectMapper objectMapper;


    public EvaluationCaseLoader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<EvaluationCase> load() {
        ClassPathResource resource = new ClassPathResource("evaluations/tickets.json");

        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<List<EvaluationCase>>() {
            });
        } catch (IOException exception) {
            throw new IllegalStateException("Could not load evaluation cases", exception);
        }
    }
}
