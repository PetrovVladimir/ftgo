package ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants;

import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;
import ru.vvpetrov91.ftgo.orderservice.api.web.OrderServiceChannels;

public class OrderServiceProxy {
    // TODO тестовый метод
    public final CommandEndpoint<TestCreateOrderCommand> testCreateOrder = CommandEndpointBuilder
            .forCommand(TestCreateOrderCommand.class)
            .withChannel(OrderServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();

    public final CommandEndpoint<RejectOrderCommand> reject = CommandEndpointBuilder
            .forCommand(RejectOrderCommand.class)
            .withChannel(OrderServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();

    public final CommandEndpoint<ApproveOrderCommand> approve = CommandEndpointBuilder
            .forCommand(ApproveOrderCommand.class)
            .withChannel(OrderServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();
}
