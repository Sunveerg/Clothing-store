package com.example.orderservice.ordersubdomain.dataacceslayer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class CustomerIdentifier {

    @Column(name = "customer_id")
    private String customerId;

    public CustomerIdentifier() {
        this.customerId = UUID.randomUUID().toString();
    }
    public CustomerIdentifier(String customerId) {
        this.customerId = customerId;
    }
}
