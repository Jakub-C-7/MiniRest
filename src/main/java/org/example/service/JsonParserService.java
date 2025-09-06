package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.example.entities.CustomerDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class JsonParserService {

    private final ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);

    /**
     * Converts a list of CustomerDetails objects to a JSON string.
     *
     * @param customerDetailsList list of CustomerDetails objects
     * @return JSON string representation of the list
     * @throws JsonProcessingException if serialization fails
     */
    //todo: use generics too
    public String writeObjectToJson(List<CustomerDetails> customerDetailsList) throws JsonProcessingException {
        return mapper.writeValueAsString(customerDetailsList);
    }

    /**
     * Parses a JSON string representing an array of objects into a list of the desired class type.
     *
     * @param jsonStringArray JSON string representing an array of objects
     * @param desiredClass an instance of the desired class type
     * @return List of objects of the desired class type
     * @param <T> the type of the desired class
     * @throws JsonProcessingException if deserialization fails
     */
    public <T> List<T> parseJsonStringArray(String jsonStringArray, T desiredClass) throws JsonProcessingException {
        return mapper.readValue(jsonStringArray, mapper.getTypeFactory().constructCollectionType(List.class, desiredClass.getClass()));
    }
}
