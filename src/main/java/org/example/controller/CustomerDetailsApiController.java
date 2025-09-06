package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.entities.CustomerDetails;
import org.example.service.CustomerDetailsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customerDetails")
@Slf4j
public class CustomerDetailsApiController {

    @Autowired
    private CustomerDetailsApiService customerDetailsApiService;

    @PostMapping(path = "/save", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<CustomerDetails>> saveCustomerDetails(@RequestBody String customerDetailsJson) {

        log.info("/save POST with body: " + customerDetailsJson);
        List<CustomerDetails> customerDetailsList = customerDetailsApiService.saveCustomerDetails(customerDetailsJson);
        return new ResponseEntity<>(customerDetailsList, HttpStatus.CREATED);

    }

    //todo: ensure contents are being returned in JSON format
    @GetMapping(path = "/get", produces = "application/json")
    public ResponseEntity<CustomerDetails> getCustomerDetails(@RequestParam String customerRef) {

        log.info("getCustomerDetails GET with customerRef: " + customerRef);
        CustomerDetails customerDetails = customerDetailsApiService.getCustomerDetails(customerRef);
        return new ResponseEntity<>(customerDetails, (customerDetails != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
