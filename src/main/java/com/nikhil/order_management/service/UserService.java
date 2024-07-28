package com.nikhil.order_management.service;

import com.nikhil.order_management.dto.OrderSummaryDTO;
import com.nikhil.order_management.dto.UserDTO;
import com.nikhil.order_management.entity.Order;
import com.nikhil.order_management.entity.User;
import com.nikhil.order_management.repository.OrderRepository;
import com.nikhil.order_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public ResponseEntity<List<OrderSummaryDTO>> getAllOrders(Integer userId){
        List<Order> orders = orderRepository.findByUserUserId(userId);
        if(orders.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<OrderSummaryDTO> orderSummaryDTOs = orders.stream()
                    .map(this::convertToOrderSummaryDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(orderSummaryDTOs, HttpStatus.OK);
        }
    }

    public ResponseEntity<UserDTO> getUser(Integer userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setOrders(user.getOrders().stream()
                    .map(this::convertToOrderSummaryDTO)
                    .collect(Collectors.toList()));
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    private OrderSummaryDTO convertToOrderSummaryDTO(Order order) {
        OrderSummaryDTO dto = new OrderSummaryDTO();
        dto.setOrderId(order.getOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(order.getOrderStatus());
        return dto;
    }

    public ResponseEntity<String> addUser(User user){
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        if(existingUser.isPresent()){
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        return new ResponseEntity<>("New User added", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteUser(Integer userId){
        Optional<User> existingUser = userRepository.findById(userId);
        if(existingUser.isPresent()){
            userRepository.deleteById(userId);
            return new ResponseEntity<>("User has been removed", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("User does not exist to be deleted", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateUser(User user){
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        if(existingUser.isPresent()){
            User updatedUser = existingUser.get();
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setRole(user.getRole());
            userRepository.save(updatedUser);
            return new ResponseEntity<>("User has been updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
    }
}
