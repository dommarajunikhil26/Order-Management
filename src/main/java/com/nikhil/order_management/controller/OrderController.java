package com.nikhil.order_management.controller;

import com.nikhil.order_management.dto.OrderDTO;
import com.nikhil.order_management.entity.Order;
import com.nikhil.order_management.entity.Product;
import com.nikhil.order_management.service.OrderService;
import com.nikhil.order_management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer orderId){
        return orderService.getOrderById(orderId);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOrder(@RequestBody OrderDTO orderDTO){
        return orderService.addOrder(orderDTO);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer orderId){
        return orderService.deleteOrder(orderId);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable Integer orderId, @RequestBody OrderDTO orderDTO){;
        return orderService.updateOrder(orderId, orderDTO);
    }
}
