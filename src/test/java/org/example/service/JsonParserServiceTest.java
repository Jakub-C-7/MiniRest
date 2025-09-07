package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.entities.CustomerDetails;
import org.example.testData.CsvParserServiceData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonParserServiceTest {

    private final CsvParserServiceData testData = new CsvParserServiceData();
    private final JsonParserService service = new JsonParserService();

    @Test
    @DisplayName("Parses customer details from JSON string array to list of CustomerDetails objects")
    void parseCustomerDetails() throws JsonProcessingException {
        List<CustomerDetails> result = service.parseJsonStringArray(testData.getCsvFileExpectedJson(), new CustomerDetails());

        assertEquals(3, result.size());

        CustomerDetails first = result.getFirst();
        assertThat(testData.getFirstCustomer()).usingRecursiveComparison().isEqualTo(first);

        CustomerDetails second = result.get(1);
        assertThat(testData.getSecondCustomer()).usingRecursiveComparison().isEqualTo(second);

        CustomerDetails third = result.get(2);
        assertThat(testData.getThirdCustomer()).usingRecursiveComparison().isEqualTo(third);
    }
}

