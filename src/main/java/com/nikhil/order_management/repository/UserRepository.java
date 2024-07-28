package com.nikhil.order_management.repository;

import com.nikhil.order_management.entity.Order;
import com.nikhil.order_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

}
