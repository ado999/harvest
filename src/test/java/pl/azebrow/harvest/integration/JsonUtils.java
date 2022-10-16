package pl.azebrow.harvest.integration;

import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class JsonUtils {

    private final ObjectMapper objectMapper;

    public JsonUtils() {
        this.objectMapper = new ObjectMapper();
    }

    public String stringify(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T parseObject(String json, Class<T> destinationClass) {
        try {
            return objectMapper.readValue(json, destinationClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Collection<T> parseCollection(String json, Class<T> destinationClass) {
        List<String> rawList = parseObject(json, List.class);
        return rawList.stream()
                .map(s -> parseObject(s, destinationClass))
                .toList();
    }
}
