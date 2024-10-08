package com.example.orderservice.ordersubdomain.dataacceslayer;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findOrderByCustomerModel_CustomerId(String customerId);


    Order findOrderByCustomerModel_CustomerIdAndAndOrderIdentifier_OrderId(String customerId, String orderId);
}
