package com.nikhil.order_management.service;

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

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public ResponseEntity<List<Order>> getAllOrders(Integer userId){
        List<Order> orders = orderRepository.findByUserUserId(userId);
        if(orders.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }

    public ResponseEntity<User> getUser(Integer userId){
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
