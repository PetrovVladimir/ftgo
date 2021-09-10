package ru.vvpetrov91.ftgo.orderservice.order.service;

import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.vvpetrov91.ftgo.orderservice.order.domain.order.*;
import ru.vvpetrov91.ftgo.orderservice.order.domain.restaurant.MenuItem;
import ru.vvpetrov91.ftgo.orderservice.order.domain.restaurant.Restaurant;
import ru.vvpetrov91.ftgo.orderservice.order.domain.restaurant.RestaurantRepository;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.CreateOrderSaga;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.CreateOrderSagaState;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Slf4j
public class OrderService {
    private OrderRepository orderRepository;
    private RestaurantRepository restaurantRepository;

    private SagaInstanceFactory sagaInstanceFactory;
    private CreateOrderSaga createOrderSaga;

    private OrderDomainEventPublisher orderAggregateEventPublisher;

    private Optional<MeterRegistry> meterRegistry;

    public OrderService(SagaInstanceFactory sagaInstanceFactory,
                        OrderRepository orderRepository,
                        DomainEventPublisher eventPublisher,
                        RestaurantRepository restaurantRepository,
                        CreateOrderSaga createOrderSaga,
                        OrderDomainEventPublisher orderAggregateEventPublisher,
                        Optional<MeterRegistry> meterRegistry) {

        this.sagaInstanceFactory = sagaInstanceFactory;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.createOrderSaga = createOrderSaga;
        this.orderAggregateEventPublisher = orderAggregateEventPublisher;
        this.meterRegistry = meterRegistry;
    }

    public Order createOrderBy(
            long consumerId,
            long restaurantId,
            DeliveryInformation deliveryInformation,
            List<MenuItemIdAndQuantity> lineItems
    ) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));

        List<OrderLineItem> orderLineItems = makeOrderLineItems(lineItems, restaurant);

        ResultWithDomainEvents<Order, OrderDomainEvent> orderAndEvents =
                Order.createOrderBy(consumerId, restaurant, deliveryInformation, orderLineItems);

        Order order = orderAndEvents.result;
        orderRepository.save(order);

        orderAggregateEventPublisher.publish(order, orderAndEvents.events);

        OrderDetails orderDetails =
                new OrderDetails(consumerId, restaurantId, orderLineItems, order.getOrderTotalPrice());

        CreateOrderSagaState data = new CreateOrderSagaState(order.getId(), orderDetails);
        sagaInstanceFactory.create(createOrderSaga, data);

        meterRegistry.ifPresent(mr -> mr.counter("placed_orders").increment());

        return order;
    }

    private List<OrderLineItem> makeOrderLineItems(List<MenuItemIdAndQuantity> lineItems, Restaurant restaurant) {
        return lineItems.stream()
                .map(li -> {
                    MenuItem om = restaurant.findMenuItem(li.getMenuItemId())
                            .orElseThrow(() -> new InvalidMenuItemIdException(li.getMenuItemId()));

                    return new OrderLineItem(li.getMenuItemId(), om.getName(), om.getPrice(), li.getQuantity());
                }).collect(toList());
    }

    public void checkCreateOrder(long orderId) {
        log.info("checkCreateOrder success");
    }
}