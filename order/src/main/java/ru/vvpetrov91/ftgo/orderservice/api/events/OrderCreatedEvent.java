package ru.vvpetrov91.ftgo.orderservice.api.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vvpetrov91.ftgo.orderservice.order.domain.order.OrderDetails;
import ru.vvpetrov91.ftgo.orderservice.order.domain.order.OrderDomainEvent;

@Data
@AllArgsConstructor
public class OrderCreatedEvent implements OrderDomainEvent {
    private OrderDetails orderDetails;
    private String deliveryAddress;
    private String restaurantName;
}