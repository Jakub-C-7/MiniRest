package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.entities.CustomerDetails;
import org.example.repository.CustomerDetailsRepository;
import org.example.testData.CsvParserServiceData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
class CustomerDetailsApiServiceTest {

    @MockitoBean
    private CustomerDetailsRepository repository;
    @MockitoBean
    private JsonParserService jsonParserService;

    @Autowired
    private CustomerDetailsApiService customerDetailsApiService;

    private final CsvParserServiceData csvParserServiceData = new CsvParserServiceData();

    @Test
    @DisplayName("Saves customer details parsed from JSON string array to repository")
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
    @DisplayName("Save customer details throws ResponseStatusException when JsonProcessingException occurs")
    public void testSaveCustomerDetailsThrowsJsonException() throws JsonProcessingException {
        //Mock behaviours
        when(jsonParserService.parseJsonStringArray(any(String.class), any(CustomerDetails.class)))
                .thenThrow(JsonProcessingException.class);
        when(repository.save(any(CustomerDetails.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        // Call service method to save customer details and expect exception
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
    @DisplayName("Get customer details by customer reference successfully")
    public void testGetCustomerDetails() {
        // Mock behaviour
        when(repository.findById("CUST001")).thenReturn(Optional.ofNullable(csvParserServiceData.getFirstCustomer()));

        // Call service method to get customer details
        CustomerDetails customerDetails = customerDetailsApiService.getCustomerDetails("CUST001");

        // Verify interaction
        verify(repository, times(1)).findById("CUST001");
        // Verify response
        assertThat(csvParserServiceData.getFirstCustomer()).usingRecursiveComparison().isEqualTo(customerDetails);
    }

    @Test
    @DisplayName("Get customer details returns null when customer not found")
    public void testGetCustomerDetailsNull() {
        // Mock behaviour
        when(repository.findById("CUST001")).thenReturn(Optional.empty());

        // Call service method to get customer details
        CustomerDetails customerDetails = customerDetailsApiService.getCustomerDetails("CUST001");

        // Verify interaction
        verify(repository, times(1)).findById("CUST001");
        // Verify response
        assertNull(customerDetails);
    }

}