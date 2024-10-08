package com.example.orderservice.ordersubdomain.dataacceslayer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class OrderIdentifier {

    @Column(name = "order_id")
    private String orderId;

    public OrderIdentifier(){this.orderId= UUID.randomUUID().toString();}

    public OrderIdentifier(String orderId) {
        this.orderId = orderId;
    }

}
