package com.nikhil.order_management.dto;

import com.nikhil.order_management.entity.OrderStatus;
import lombok.Data;

import java.util.Date;

@Data
public class OrderSummaryDTO {
    private int orderId;
    private Date orderDate;
    private OrderStatus orderStatus;
}
