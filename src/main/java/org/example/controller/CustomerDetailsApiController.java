package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.entities.CustomerDetails;
import org.example.service.CustomerDetailsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to handle customer details related API requests.
 * Provides endpoints to save and retrieve customer details.
 */
@RestController
@RequestMapping("/customerDetails")
@Slf4j
public class CustomerDetailsApiController {

    @Autowired
    private CustomerDetailsApiService customerDetailsApiService;

    @PostMapping(path = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<CustomerDetails>> saveCustomerDetails(@RequestBody String customerDetailsJson) {

        log.info("/customerDetails/save POST request with body: " + customerDetailsJson);
        List<CustomerDetails> customerDetailsList = customerDetailsApiService.saveCustomerDetails(customerDetailsJson);
        return new ResponseEntity<>(customerDetailsList, HttpStatus.CREATED);
    }

    @GetMapping(path = "/get", produces = "application/json")
    public ResponseEntity<CustomerDetails> getCustomerDetails(@RequestParam String customerRef) {

        log.info("/customerDetails/get GET request with customerRef: " + customerRef);
        CustomerDetails customerDetails = customerDetailsApiService.getCustomerDetails(customerRef);
        return new ResponseEntity<>(customerDetails, (customerDetails != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
