package com.afterburner.common.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting list to JSON string", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<String>>() {});
        } catch (IOException jsonException) {
            // Handle values stored as PostgreSQL array or JSON object
            try {
                if (dbData.startsWith("{") && dbData.endsWith("}")) {
                    String trimmed = dbData.substring(1, dbData.length() - 1).trim();
                    if (trimmed.isEmpty()) {
                        return List.of();
                    }
                    return Arrays.stream(trimmed.split(","))
                            .map(s -> s.replaceAll("^\\\"|\\\"$", "").trim())
                            .collect(Collectors.toList());
                }
                // Try to read as generic map and return keys
                Map<String, Object> map = objectMapper.readValue(dbData, new TypeReference<Map<String, Object>>() {});
                return new ArrayList<>(map.keySet());
            } catch (IOException ignore) {
                throw new IllegalArgumentException("Error converting JSON string to list", jsonException);
            }
        }
    }
}
