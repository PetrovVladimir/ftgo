package ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.vvpetrov91.ftgo.orderservice.order.domain.order.OrderDetails;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants.RejectOrderCommand;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants.TestCreateOrderCommand;

@Slf4j
@Data
public class CreateOrderSagaState {
    private Long orderId;
    private OrderDetails orderDetails;
    private long ticketId;

    public CreateOrderSagaState(Long orderId, OrderDetails orderDetails) {
        this.orderId = orderId;
        this.orderDetails = orderDetails;
    }

    RejectOrderCommand makeRejectOrderCommand() {
        return new RejectOrderCommand(getOrderId());
    }

    TestCreateOrderCommand testCreateOrderCommand() {
        return new TestCreateOrderCommand(getOrderId());
    }
}