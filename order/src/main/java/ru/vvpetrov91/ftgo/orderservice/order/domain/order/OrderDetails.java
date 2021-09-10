package ru.vvpetrov91.ftgo.orderservice.order.domain.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDetails {
    private long consumerId;
    private long restaurantId;
    private List<OrderLineItem> lineItems;
    private BigDecimal orderTotal;

    public OrderDetails(long consumerId, long restaurantId, List<OrderLineItem> lineItems, BigDecimal orderTotal) {
        this.consumerId = consumerId;
        this.restaurantId = restaurantId;
        this.lineItems = lineItems;
        this.orderTotal = orderTotal;
    }
}