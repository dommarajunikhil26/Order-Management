package com.nikhil.order_management.controller;

import com.nikhil.order_management.entity.Order;
import com.nikhil.order_management.entity.User;
import com.nikhil.order_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("{userId}/orders")
    public ResponseEntity<List<Order>> getAllOrders(@PathVariable Integer userId){
        return userService.getAllOrders(userId);
    }

    @GetMapping("{userId}")
    public ResponseEntity<User> getUser(@PathVariable Integer userId){
        return userService.getUser(userId);
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> postUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId){
        return userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Integer userId, @RequestBody User user){
        user.setUserId(userId);
        return userService.updateUser(user);
    }

}
