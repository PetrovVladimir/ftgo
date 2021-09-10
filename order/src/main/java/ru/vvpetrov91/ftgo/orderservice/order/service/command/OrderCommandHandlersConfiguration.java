package ru.vvpetrov91.ftgo.orderservice.order.service.command;

import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.events.publisher.TramEventsPublisherConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.vvpetrov91.ftgo.orderservice.order.service.command.common.CommonConfiguration;

@Configuration
@Import(
        {
                SagaParticipantConfiguration.class,
                TramEventsPublisherConfiguration.class,
                CommonConfiguration.class,
                SagaParticipantConfiguration.class
        }
)
@Slf4j
public class OrderCommandHandlersConfiguration {
    @Bean
    public OrderCommandHandlers orderCommandHandlers() {
        return new OrderCommandHandlers();
    }

    @Bean
    public SagaCommandDispatcher orderCommandHandlersDispatcher(
            OrderCommandHandlers orderCommandHandlers, SagaCommandDispatcherFactory sagaCommandDispatcherFactory
    ) {
        return sagaCommandDispatcherFactory
                .make("orderService", orderCommandHandlers.commandHandlers());
    }
}