package ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class RejectOrderCommand extends OrderCommand{
    public RejectOrderCommand(long orderId) {

        super(orderId);
    }
}
