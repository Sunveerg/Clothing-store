package com.example.apigateway.presentationlayer.order;



import com.example.apigateway.domainclientlayer.order.Status;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    //Department
    String name;
    Integer headCount;
    //Product
    String productName;
    Integer productStock;
}
