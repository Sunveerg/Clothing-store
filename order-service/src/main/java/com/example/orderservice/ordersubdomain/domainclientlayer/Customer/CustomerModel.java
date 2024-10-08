package com.example.orderservice.ordersubdomain.domainclientlayer.Customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {

    String customerId;
    String firstName;
    String lastName;

}