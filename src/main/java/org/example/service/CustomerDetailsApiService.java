package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.entities.CustomerDetails;
import org.example.repository.CustomerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Service class to handle business logic for customer details.
 * Provides methods to save and retrieve customer details.
 */
@Slf4j
@Service
public class CustomerDetailsApiService {

    @Autowired
    private JsonParserService jsonParserService;
    @Autowired
    private CustomerDetailsRepository repository;

    /**
     * Save customer details from a JSON string array.
     * Parses the JSON string into a list of CustomerDetails objects and saves each to the repository
     *
     * @param customerDetailsJson JSON string array of customer details
     * @return List of saved CustomerDetails objects
     */
    public List<CustomerDetails> saveCustomerDetails(String customerDetailsJson) {

        List<CustomerDetails> customerDetailsList;
        try {
            customerDetailsList = jsonParserService.parseJsonStringArray(customerDetailsJson, new CustomerDetails());
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JsonProcessingException, invalid JSON format.");
        }

        for (CustomerDetails customerDetails : customerDetailsList) {
            log.info("Saving CustomerDetails with ref: " + customerDetails.getCustomerRef());
            repository.save(customerDetails);
        }
        return customerDetailsList;

    }

    /**
     * Retrieve customer details by customer reference.
     *
     * @param customerRef the customer reference identifier
     * @return CustomerDetails object if found, otherwise null
     */
    public CustomerDetails getCustomerDetails(String customerRef) {

        return repository.findById(Optional.of(customerRef).orElse(null)).orElse(null);
    }

}
