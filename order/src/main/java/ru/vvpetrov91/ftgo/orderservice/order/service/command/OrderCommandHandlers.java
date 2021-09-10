package ru.vvpetrov91.ftgo.orderservice.order.service.command;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants.TestCreateOrderCommand;
import ru.vvpetrov91.ftgo.orderservice.order.service.OrderService;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

@Slf4j
public class OrderCommandHandlers {
    @Autowired
    private OrderService orderService;

    public CommandHandlers commandHandlers() {
        return SagaCommandHandlersBuilder
                .fromChannel("orderService")
                .onMessage(TestCreateOrderCommand.class, this::testCreateOrderCheck)
                .build();

    }

    private Message testCreateOrderCheck(CommandMessage<TestCreateOrderCommand> commandMessage) {
        long orderId = commandMessage.getCommand().getOrderId();
        orderService.checkCreateOrder(orderId);

        return withSuccess();
    }
}