package org.example.entities;

import jakarta.persistence.Id;
import lombok.*;
import jakarta.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDetails {

    //todo: are duplicate IDs being allowed
    @Id
    private String customerRef;
    private String customerName;
    private String addressLine1;
    private String addressLine2;
    private String town;
    private String county;
    private String country;
    private String postcode;

}
