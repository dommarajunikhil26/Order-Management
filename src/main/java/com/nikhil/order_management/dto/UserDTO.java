package com.nikhil.order_management.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private int userId;
    private String firstName;
    private String lastName;
    private List<OrderSummaryDTO> orders;
}
