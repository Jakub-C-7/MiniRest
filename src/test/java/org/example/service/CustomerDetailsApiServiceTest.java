package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.entities.CustomerDetails;
import org.example.repository.CustomerDetailsRepository;
import org.example.testData.CsvParserServiceData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerDetailsApiServiceTest {

    @MockitoBean
    private CustomerDetailsRepository repository;

    @MockitoBean
    private JsonParserService jsonParserService;

    @Autowired
    private CustomerDetailsApiService customerDetailsApiService;

    private CsvParserServiceData csvParserServiceData = new CsvParserServiceData();

    @Test
    public void testSaveCustomerDetails() throws JsonProcessingException {

        //Mock behaviours
        when(jsonParserService.parseJsonStringArray(any(String.class), any(CustomerDetails.class)))
                .thenReturn(csvParserServiceData.getAllCustomerDetails());
        when(repository.save(any(CustomerDetails.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        List<CustomerDetails> responseEntity = customerDetailsApiService.saveCustomerDetails(csvParserServiceData.getCsvFileExpectedJson());

        assertThat(csvParserServiceData.getAllCustomerDetails()).usingRecursiveComparison().isEqualTo(responseEntity);

        verify(jsonParserService, times(1)).parseJsonStringArray(any(String.class), any(CustomerDetails.class));
        verify(repository, times(3)).save(any(CustomerDetails.class));

    }

    @Test
    public void testSaveCustomerDetailsThrowsJsonException() throws JsonProcessingException {

        //Mock behaviours
        when(jsonParserService.parseJsonStringArray(any(String.class), any(CustomerDetails.class)))
                .thenThrow(JsonProcessingException.class);
        when(repository.save(any(CustomerDetails.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            customerDetailsApiService.saveCustomerDetails(csvParserServiceData.getCsvFileExpectedJson());
        });

        // Verify interactions
        verify(jsonParserService, times(1)).parseJsonStringArray(any(String.class), any(CustomerDetails.class));
        verify(repository, never()).save(any(CustomerDetails.class));
        // Verify response
        assertEquals("400 BAD_REQUEST \"JsonProcessingException, invalid JSON format.\"", exception.getMessage());

    }

    @Test
    public void testGetCustomerDetails() {

        // Mock behaviour
        when(repository.findById("CUST001")).thenReturn(Optional.ofNullable(csvParserServiceData.getFirstCustomer()));

        CustomerDetails customerDetails = customerDetailsApiService.getCustomerDetails("CUST001");

        // Verify interaction
        verify(repository, times(1)).findById("CUST001");
        // Verify response
        assertThat(csvParserServiceData.getFirstCustomer()).usingRecursiveComparison().isEqualTo(customerDetails);

    }

    @Test
    public void testGetCustomerDetailsNull() {

        // Mock behaviour
        when(repository.findById("CUST001")).thenReturn(Optional.empty());

        CustomerDetails customerDetails = customerDetailsApiService.getCustomerDetails("CUST001");

        // Verify interaction
        verify(repository, times(1)).findById("CUST001");
        // Verify response
        assertNull(customerDetails);

    }

}