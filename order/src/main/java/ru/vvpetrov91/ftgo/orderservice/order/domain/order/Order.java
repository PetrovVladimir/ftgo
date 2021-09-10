package ru.vvpetrov91.ftgo.orderservice.order.domain.order;

import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vvpetrov91.ftgo.orderservice.api.events.OrderCreatedEvent;
import ru.vvpetrov91.ftgo.orderservice.order.domain.restaurant.Restaurant;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.singletonList;
import static ru.vvpetrov91.ftgo.orderservice.order.domain.order.OrderState.APPROVAL_PENDING;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    public static ResultWithDomainEvents<Order, OrderDomainEvent> createOrderBy(
            long consumerId,
            Restaurant restaurant,
            DeliveryInformation deliveryInformation,
            List<OrderLineItem> orderLineItems
    ) {
        Order order = new Order(consumerId, restaurant.getId(), deliveryInformation, orderLineItems);
        List<OrderDomainEvent> events = singletonList(
                new OrderCreatedEvent(
                        new OrderDetails(consumerId, restaurant.getId(), orderLineItems, order.getOrderTotalPrice()),
                        deliveryInformation.getDeliveryAddress(),
                        restaurant.getName()
                )
        );

        return new ResultWithDomainEvents<>(order, events);
    }

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    private Long consumerId;

    private Long restaurantId;

    @ElementCollection
    @CollectionTable(name = "order_line_items")
    private List<OrderLineItem> orderLineItems;

    @Embedded
    private DeliveryInformation deliveryInformation;

    @Embedded
    @AttributeOverride(name = "paymentToken", column = @Column(name = "payment_token"))
    private PaymentInformation paymentInformation;

    @Transient
    private BigDecimal orderMinimum = new BigDecimal(Integer.MAX_VALUE);

    public Order(
            long consumerId,
            long restaurantId,
            DeliveryInformation deliveryInformation,
            List<OrderLineItem> orderLineItems
    ) {
        this.consumerId = consumerId;
        this.restaurantId = restaurantId;
        this.deliveryInformation = deliveryInformation;
        this.orderLineItems = orderLineItems;
        this.orderState = APPROVAL_PENDING;
    }

    public BigDecimal getOrderTotalPrice() {
        return orderLineItems.stream().map(OrderLineItem::getTotalPrice).reduce(new BigDecimal(0), BigDecimal::add);
    }
}