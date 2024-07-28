package com.nikhil.order_management.repository;

import com.nikhil.order_management.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserUserId(Integer userId);
}
