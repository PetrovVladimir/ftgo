package ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas;

import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants.AccountingServiceProxy;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants.ConsumerServiceProxy;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants.KitchenServiceProxy;
import ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants.OrderServiceProxy;


public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaState> {
    private SagaDefinition<CreateOrderSagaState> sagaDefinition;

    public CreateOrderSaga(
            OrderServiceProxy orderService,
            ConsumerServiceProxy consumerService,
            KitchenServiceProxy kitchenService,
            AccountingServiceProxy accountingService
    ) {
        this.sagaDefinition
                =
                step()
                        .withCompensation(orderService.reject, CreateOrderSagaState::makeRejectOrderCommand)
                .step()
                        .invokeParticipant(orderService.testCreateOrder, CreateOrderSagaState::testCreateOrderCommand)
                .build();
    }

    @Override
    public SagaDefinition<CreateOrderSagaState> getSagaDefinition() {
        return sagaDefinition;
    }
}