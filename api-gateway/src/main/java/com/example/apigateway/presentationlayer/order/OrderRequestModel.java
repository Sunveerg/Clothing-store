package com.example.apigateway.presentationlayer.order;



import com.example.apigateway.domainclientlayer.order.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestModel {

    String orderDate;
    Status status;
    String departmentId;
    String employeeId;
    String productId;
    String customerId;
}
