package com.example.orderservice.ordersubdomain.businesslayer;


import com.example.orderservice.ordersubdomain.dataacceslayer.Order;
import com.example.orderservice.ordersubdomain.dataacceslayer.OrderIdentifier;
import com.example.orderservice.ordersubdomain.dataacceslayer.OrderRepository;
import com.example.orderservice.ordersubdomain.domainclientlayer.Customer.CustomerModel;
import com.example.orderservice.ordersubdomain.domainclientlayer.Customer.CustomerServiceClient;
import com.example.orderservice.ordersubdomain.domainclientlayer.Employee.EmployeeModel;
import com.example.orderservice.ordersubdomain.domainclientlayer.Employee.EmployeeServiceClient;
import com.example.orderservice.ordersubdomain.domainclientlayer.Product.ProductModel;
import com.example.orderservice.ordersubdomain.domainclientlayer.Product.ProductServiceClient;
import com.example.orderservice.ordersubdomain.mapperlayer.OrderRequestMapper;
import com.example.orderservice.ordersubdomain.mapperlayer.OrderResponseMapper;
import com.example.orderservice.ordersubdomain.presentationlayer.OrderRequestModel;
import com.example.orderservice.ordersubdomain.presentationlayer.OrderResponseModel;
import com.example.orderservice.utils.exceptions.InvalidInputException;
import com.example.orderservice.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{


    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final CustomerServiceClient customerServiceClient;
    private final OrderRequestMapper orderRequestMapper;
    private final OrderResponseMapper orderResponseMapper;



    @Override
    public List<OrderResponseModel> getAllOrder(String customerId) {
        CustomerModel customerModel = customerServiceClient.getCustomerByCustomerId(customerId);
        if(customerModel==null){
            throw new InvalidInputException("CustomerId provided is Invalid " + customerId);
        }

        List<Order> orders = orderRepository.findOrderByCustomerModel_CustomerId(customerId);

        return orderResponseMapper.entityListToResponseModelList(orders);
    }

    @Override
    public OrderResponseModel getOrderById(String orderId, String customerId) {
        CustomerModel customerModel = customerServiceClient.getCustomerByCustomerId(customerId);
        if(customerModel==null){
            throw new InvalidInputException("CustomerId provided is Invalid " + customerId);
        }

        Order order = orderRepository.findOrderByCustomerModel_CustomerIdAndAndOrderIdentifier_OrderId(customerId, orderId);
        if(order == null){
            throw new NotFoundException("Order provided is invalid " + orderId);
        }


        return orderResponseMapper.entityToResponseModel(order);
    }

    @Override
    public OrderResponseModel addOrder(OrderRequestModel orderRequestModel, String customerId) {
        CustomerModel customer = customerServiceClient.getCustomerByCustomerId(customerId);
        if(customer==null){
            throw new InvalidInputException("PlayerId provided is Invalid " + customerId);
        }

        EmployeeModel employee = employeeServiceClient.getEmployeeByEmployeeId(orderRequestModel.getEmployeeId());
        if(employee == null) {
            throw new InvalidInputException("EmployeeId is invalid "+ orderRequestModel.getEmployeeId());
        }

        ProductModel product = productServiceClient.getProductByProductId(orderRequestModel.getProductId());
        if(product==null){
            throw new InvalidInputException("ProductId provided is invalid " + orderRequestModel.getProductId());
        }

        //customerServiceClient.patchPlayer(playerId, transferRequestModel.getToEmployeeId());
        //log.debug("toEmployeeId" + transferRequestModel.getToEmployeeId());

        Order order = orderRequestMapper.requestModelToEntity(orderRequestModel,new OrderIdentifier(),
                product, employee, customer);

        if (order.getProductModel().getProductStock() < 0) {
            throw new InvalidInputException("No stock available");
        }

        Order savedOrder = orderRepository.save(order);
        return orderResponseMapper.entityToResponseModel(savedOrder);
    }

    @Override
    public OrderResponseModel updateOrder(OrderRequestModel orderRequestModel, String customerId, String orderId) {

        Order existintOrder = orderRepository.findOrderByCustomerModel_CustomerIdAndAndOrderIdentifier_OrderId(customerId, orderId);
        if(existintOrder == null){
            throw new NotFoundException("Order provided is invalid " + orderId);
        }

        CustomerModel customer = customerServiceClient.getCustomerByCustomerId(customerId);
        if(customer==null){
            throw new InvalidInputException("PlayerId provided is Invalid " + customerId);
        }

        EmployeeModel employee = employeeServiceClient.getEmployeeByEmployeeId(orderRequestModel.getEmployeeId());
        if(employee == null) {
            throw new InvalidInputException("EmployeeId is invalid "+ orderRequestModel.getEmployeeId());
        }

        ProductModel product = productServiceClient.getProductByProductId(orderRequestModel.getProductId());
        if(product==null){
            throw new InvalidInputException("ProductId provided is invalid " + orderRequestModel.getProductId());
        }

        Order order = orderRequestMapper.requestModelToEntity(orderRequestModel, existintOrder.getOrderIdentifier(),
                product, employee, customer);
        order.setId(existintOrder.getId());

        if (order.getProductModel().getProductStock() < 0) {
            throw new InvalidInputException("No stock available");
        }


        //playerServiceClient.patchPlayer(playerId, transferRequestModel.getToEmployeeId());


        Order savedOrder = orderRepository.save(order);

        // transfer.getPlayerModel().setPosition(transferRequestModel.getToEmployeeId());


        return orderResponseMapper.entityToResponseModel(savedOrder);

    }

    @Override
    public void deleteOrder(String orderId, String customerId) {
        Order order = orderRepository.findOrderByCustomerModel_CustomerIdAndAndOrderIdentifier_OrderId(customerId, orderId);
        orderRepository.delete(order);
    }
}
