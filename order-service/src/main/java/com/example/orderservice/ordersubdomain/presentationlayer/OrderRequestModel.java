package com.example.orderservice.ordersubdomain.presentationlayer;


import com.example.orderservice.ordersubdomain.dataacceslayer.Status;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class OrderRequestModel {

    String orderDate;
    Status status;
    String employeeId;
    String productId;
    String customerId;
    String productName;
    Integer productStock;
}
