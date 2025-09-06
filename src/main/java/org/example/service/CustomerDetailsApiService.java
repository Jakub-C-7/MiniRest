package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.entities.CustomerDetails;
import org.example.repository.CustomerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerDetailsApiService {

    @Autowired
    private JsonParserService jsonParserService;
    @Autowired
    private CustomerDetailsRepository repository;

    public List<CustomerDetails> saveCustomerDetails(String customerDetailsJson) {

        List<CustomerDetails> customerDetailsList;
        try {
            customerDetailsList = jsonParserService.parseJsonStringArray(customerDetailsJson, new CustomerDetails());
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JsonProcessingException, invalid JSON format.");
        }

        for (CustomerDetails customerDetails : customerDetailsList) {
            log.info("Saving CustomerDetails: " + customerDetails);
            repository.save(customerDetails);
        }

        return customerDetailsList;

    }

    public CustomerDetails getCustomerDetails(String customerRef) {
        CustomerDetails customerDetails =
                repository.findById(Optional.of(customerRef).orElse(null)).orElse(null);

        if (customerDetails == null) {
            log.warn("CustomerDetails not found for customerRef: " + customerRef);
        }

        return customerDetails;
    }


}
