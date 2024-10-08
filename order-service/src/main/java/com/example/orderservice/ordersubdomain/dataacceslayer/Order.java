package com.example.orderservice.ordersubdomain.dataacceslayer;


import com.example.orderservice.ordersubdomain.domainclientlayer.Customer.CustomerModel;
import com.example.orderservice.ordersubdomain.domainclientlayer.Employee.EmployeeModel;
import com.example.orderservice.ordersubdomain.domainclientlayer.Product.ProductModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    private OrderIdentifier orderIdentifier;

    private String orderDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Order( String orderDate, Status status, CustomerModel customerModel, EmployeeModel employeeModel, ProductModel productModel) {

        this.orderIdentifier = new OrderIdentifier();
        this.orderDate = orderDate;
        this.status = status;
        this.customerModel = customerModel;
        this.employeeModel = employeeModel;
        this.productModel = productModel;
    }

    @Embedded
    private CustomerModel customerModel;
    @Embedded
    private EmployeeModel employeeModel;
    @Embedded
    private ProductModel productModel;
}
