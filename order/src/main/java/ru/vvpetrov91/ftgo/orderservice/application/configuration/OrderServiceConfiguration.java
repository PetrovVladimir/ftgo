package ru.vvpetrov91.ftgo.orderservice.application.configuration;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import io.eventuate.tram.spring.events.publisher.TramEventsPublisherConfiguration;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.vvpetrov91.ftgo.orderservice.order.domain.order.OrderDomainEventPublisher;
import ru.vvpetrov91.ftgo.orderservice.order.domain.order.OrderRepository;
import ru.vvpetrov91.ftgo.orderservice.order.domain.restaurant.RestaurantRepository;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.CreateOrderSaga;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants.AccountingServiceProxy;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants.ConsumerServiceProxy;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants.KitchenServiceProxy;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants.OrderServiceProxy;
import ru.vvpetrov91.ftgo.orderservice.order.service.OrderService;
import ru.vvpetrov91.ftgo.orderservice.order.service.command.common.CommonConfiguration;

import java.util.Optional;

@Configuration
@Import({TramEventsPublisherConfiguration.class, SagaOrchestratorConfiguration.class, CommonConfiguration.class})
public class OrderServiceConfiguration {
    @Bean
    public OrderService orderService(SagaInstanceFactory sagaInstanceFactory,
                                     RestaurantRepository restaurantRepository,
                                     OrderRepository orderRepository,
                                     DomainEventPublisher eventPublisher,
                                     CreateOrderSaga createOrderSaga,
                                     OrderDomainEventPublisher orderAggregateEventPublisher,
                                     Optional<MeterRegistry> meterRegistry) {

        return new OrderService(sagaInstanceFactory, orderRepository, eventPublisher, restaurantRepository,
                createOrderSaga, orderAggregateEventPublisher, meterRegistry);
    }

    @Bean
    public CreateOrderSaga createOrderSaga(OrderServiceProxy orderService, ConsumerServiceProxy consumerService, KitchenServiceProxy kitchenServiceProxy, AccountingServiceProxy accountingService) {
        return new CreateOrderSaga(orderService, consumerService, kitchenServiceProxy, accountingService);
    }

    @Bean
    public KitchenServiceProxy kitchenServiceProxy() {
        return new KitchenServiceProxy();
    }

    @Bean
    public OrderServiceProxy orderServiceProxy() {
        return new OrderServiceProxy();
    }

    @Bean
    public ConsumerServiceProxy consumerServiceProxy() {
        return new ConsumerServiceProxy();
    }

    @Bean
    public AccountingServiceProxy accountingServiceProxy() {
        return new AccountingServiceProxy();
    }

    @Bean
    public OrderDomainEventPublisher orderAggregateEventPublisher(DomainEventPublisher eventPublisher) {
        return new OrderDomainEventPublisher(eventPublisher);
    }

    @Bean
    public MeterRegistryCustomizer meterRegistryCustomizer(@Value("${spring.application.name}") String serviceName) {
        return registry -> registry.config().commonTags("service", serviceName);
    }
}
