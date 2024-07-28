package com.nikhil.order_management.dto;

import com.nikhil.order_management.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private int userId;
    private String orderStatus;
    private List<OrderItemDTO> orderItems;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemDTO {
        private int productId;
        private int quantity;
    }
}
