package ru.vvpetrov91.ftgo.orderservice.api.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private long restaurantId;
    private long consumerId;
    private LocalDateTime deliveryTime;
    private List<LineItem> lineItems;
    private String deliveryAddress;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LineItem {
        private Long menuItemId;
        private int quantity;
    }
}