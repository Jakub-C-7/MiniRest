package org.example.service;

import org.example.controller.CustomerDetailsApiController;
import org.example.repository.CustomerDetailsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest
class CsvParserServiceTest {

    @Autowired
    public CsvParserService service;
    @Autowired
    public CustomerDetailsRepository repository;
    @MockitoBean
    public CustomerDetailsApiController controller;

    private final String csvFilePath = "src/test/resources/testData/sampleData.csv";
    private final String invalidCsvFilePath = "src/test/resources/testData/invalidData.csv";

    @Test
    @DisplayName("processCsv calls the controller with Json String to save customer details")
    void testProcessCsv() throws Exception {
        service.processCsv(csvFilePath);
        verify(controller, times(1)).saveCustomerDetails(any(String.class));
    }

    @Test
    @DisplayName("processCsv calls the controller with Json String to save customer details")
    void testProcessCsvInvalid() {
        assertThrows(Exception.class, () -> service.processCsv(invalidCsvFilePath));
        verify(controller, times(0)).saveCustomerDetails(any(String.class));
    }

}