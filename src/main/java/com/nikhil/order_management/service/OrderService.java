package com.nikhil.order_management.service;

import com.nikhil.order_management.dto.OrderDTO;
import com.nikhil.order_management.entity.*;
import com.nikhil.order_management.repository.OrderItemRepository;
import com.nikhil.order_management.repository.OrderRepository;
import com.nikhil.order_management.repository.ProductRepository;
import com.nikhil.order_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Order> getOrderById(Integer orderId) {
        Optional<Order> existingOrder = orderRepository.findById(orderId);
        return existingOrder.map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> addOrder(OrderDTO orderDTO) {
        Optional<User> userOpt = userRepository.findById(orderDTO.getUserId());
        if (!userOpt.isPresent()) {
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }

        User user = userOpt.get();
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setOrderStatus(OrderStatus.PENDING);

        order = orderRepository.save(order);

        for (OrderDTO.OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            Optional<Product> productOpt = productRepository.findById(itemDTO.getProductId());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                if (product.getQuantityAvailable() >= itemDTO.getQuantity()) {
                    product.reduceQuantity(itemDTO.getQuantity());
                    productRepository.save(product);

                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    orderItem.setQuantity(itemDTO.getQuantity());
                    orderItemRepository.save(orderItem);
                } else {
                    return new ResponseEntity<>("Insufficient quantity for product: " + product.getProductName(), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Product not found: " + itemDTO.getProductId(), HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>("Order has been placed", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteOrder(Integer orderId) {
        Optional<Order> existingOrder = orderRepository.findById(orderId);
        if(existingOrder.isPresent()){
            orderRepository.deleteById(orderId);
            return new ResponseEntity<>("Order has been deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Order does not exist", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> updateOrder(Integer orderId, OrderDTO orderDTO) {
        Optional<Order> existingOrder = orderRepository.findById(orderId);
        if (existingOrder.isPresent()) {
            Order updatedOrder = existingOrder.get();
            updatedOrder.setOrderStatus(OrderStatus.valueOf(orderDTO.getOrderStatus().toUpperCase()));
            orderRepository.save(updatedOrder);
            return new ResponseEntity<>("Order status has been updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Order does not exist", HttpStatus.NOT_FOUND);
    }
}
