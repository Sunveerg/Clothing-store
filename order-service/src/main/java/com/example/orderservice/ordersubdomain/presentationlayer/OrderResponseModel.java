package com.example.orderservice.ordersubdomain.presentationlayer;


import com.example.orderservice.ordersubdomain.dataacceslayer.Status;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
public class OrderResponseModel extends RepresentationModel<OrderResponseModel> {

    String orderId;
    String orderDate;
    Status status;
    String employeeId;
    String productId;
    String customerId;
    //Customer
    String firstName;
    String lastName;
    //Employee
    Double commissionRate;
    //Product
    String productName;
    Integer productStock;
}
