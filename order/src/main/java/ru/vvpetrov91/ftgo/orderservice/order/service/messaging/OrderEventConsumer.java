package ru.vvpetrov91.ftgo.orderservice.order.service.messaging;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.vvpetrov91.ftgo.orderservice.api.events.OrderCreatedEvent;
import ru.vvpetrov91.ftgo.orderservice.order.service.OrderService;

@AllArgsConstructor
@Slf4j
public class OrderEventConsumer {
    private OrderService orderService;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType("ru.vvpetrov91.ftgo.orderservice.order.domain.order.Order")
                .onEvent(OrderCreatedEvent.class, this::test)
                .build();
    }

    private void test(DomainEventEnvelope<OrderCreatedEvent> de) {
        log.info("test");
    }
}