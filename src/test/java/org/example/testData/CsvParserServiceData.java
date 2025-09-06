package org.example.testData;

import lombok.Getter;
import org.example.entities.CustomerDetails;

import java.util.List;

@Getter
public class CsvParserServiceData {

    private final String csvFileExpectedJson = """
            [ {
                "customerRef" : "CUST001",
                "customerName" : "John Smith",
                "addressLine1" : "1 Main St",
                "addressLine2" : "Apt 4B",
                "town" : "Springfield",
                "county" : "Essex",
                "country" : "UK",
                "postcode" : "SP1 2AB"
              }, {
                "customerRef" : "CUST002",
                "customerName" : "Jane Doe",
                "addressLine1" : "2 Elm St",
                "addressLine2" : "Apt 1",
                "town" : "Manchester",
                "county" : "Greater Manchester",
                "country" : "UK",
                "postcode" : "M1 3CD"
              }, {
                "customerRef" : "CUST003",
                "customerName" : "Acme Corp",
                "addressLine1" : "3 Oak Ave",
                "addressLine2" : "Suite 12",
                "town" : "Leeds",
                "county" : "West Yorkshire",
                "country" : "UK",
                "postcode" : "LS1 4EF"
              } ]
            """;

    private final CustomerDetails firstCustomer = new CustomerDetails(
            "CUST001",
            "John Smith",
            "1 Main St",
            "Apt 4B",
            "Springfield",
            "Essex",
            "UK",
            "SP1 2AB"
    );

    private final CustomerDetails secondCustomer = new CustomerDetails(
            "CUST002",
            "Jane Doe",
            "2 Elm St",
            "Apt 1",
            "Manchester",
            "Greater Manchester",
            "UK",
            "M1 3CD"
    );

    private final CustomerDetails thirdCustomer = new CustomerDetails(
            "CUST003",
            "Acme Corp",
            "3 Oak Ave",
            "Suite 12",
            "Leeds",
            "West Yorkshire",
            "UK",
            "LS1 4EF"
    );

    private final List<CustomerDetails> allCustomerDetails = List.of(firstCustomer, secondCustomer, thirdCustomer);

}
