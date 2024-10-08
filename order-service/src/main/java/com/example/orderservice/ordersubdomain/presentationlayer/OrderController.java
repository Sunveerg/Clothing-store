package com.example.orderservice.ordersubdomain.presentationlayer;



import com.example.orderservice.ordersubdomain.businesslayer.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders/{customerId}")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) { this.orderService = orderService; }

    @GetMapping
    public ResponseEntity<List<OrderResponseModel>> getAllOrders(@PathVariable String customerId){

        return ResponseEntity.ok().body(orderService.getAllOrder(customerId));

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseModel> getOrderById(@PathVariable String customerId, @PathVariable String orderId){

        return ResponseEntity.ok().body(orderService.getOrderById(customerId,orderId));

    }

    @PostMapping
    public ResponseEntity<OrderResponseModel> addOrder(@PathVariable String customerId, @RequestBody OrderRequestModel orderRequestModel){

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addOrder(orderRequestModel, customerId));

    }

    @PutMapping(value = "/{orderId}", produces = "application/json")
    public ResponseEntity<OrderResponseModel> updateOrder(@PathVariable String customerId,
                                                          @PathVariable String orderId,
                                                          @RequestBody OrderRequestModel orderRequestModel){

        return ResponseEntity.ok().body(orderService.updateOrder(orderRequestModel,orderId,customerId));

    }

    @DeleteMapping(value="/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String customerId,
                                            @PathVariable String orderId){
        orderService.deleteOrder(orderId, customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
