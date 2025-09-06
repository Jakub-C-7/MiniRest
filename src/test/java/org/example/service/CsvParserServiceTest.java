package org.example.service;

import org.example.controller.CustomerDetailsApiController;
import org.example.testData.CsvParserServiceData;
import org.example.repository.CustomerDetailsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CsvParserServiceTest {

    @Autowired
    public CsvParserService service;
    @Autowired
    public CustomerDetailsRepository repository;

    @MockitoBean
    public CustomerDetailsApiController controller;

    private final CsvParserServiceData testData = new CsvParserServiceData();

    @Test
    @DisplayName("processCsv calls the controller with Json String to save customer details")
    void testProcessCsv() throws Exception {
        service.processCsv("src/main/resources/data.csv");
        verify(controller, times(1)).saveCustomerDetails(any(String.class));
    }

    @Test
    @DisplayName("processCsv calls the controller with Json String to save customer details")
    void testProcessCsvInvalid() {
        assertThrows(Exception.class,() ->  service.processCsv("src/test/resources/testData/invalidData.csv"));
        verify(controller, times(0)).saveCustomerDetails(any(String.class));
    }

}