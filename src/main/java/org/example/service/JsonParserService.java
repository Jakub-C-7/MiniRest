package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for parsing JSON strings to objects and vice versa.
 */
@Service
@Slf4j
public class JsonParserService {

    private final ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);

    /**
     * Converts a list of CustomerDetails objects to a JSON string.
     *
     * @param objectList list of Objects to be converted to JSON
     * @param <T>        the type of the objects in the list
     * @return JSON string representation of the list
     * @throws JsonProcessingException if serialization fails
     */
    public <T> String writeObjectToJson(List<T> objectList) throws JsonProcessingException {
        return mapper.writeValueAsString(objectList);
    }

    /**
     * Parses a JSON string representing an array of objects into a list of the desired class type.
     *
     * @param jsonStringArray JSON string representing an array of objects
     * @param desiredClass    an instance of the desired class type
     * @param <T>             the type of the desired class
     * @return List of objects of the desired class type
     * @throws JsonProcessingException if deserialization fails
     */
    public <T> List<T> parseJsonStringArray(String jsonStringArray, T desiredClass) throws JsonProcessingException {
        return mapper.readValue(jsonStringArray, mapper.getTypeFactory().constructCollectionType(List.class, desiredClass.getClass()));
    }
}
