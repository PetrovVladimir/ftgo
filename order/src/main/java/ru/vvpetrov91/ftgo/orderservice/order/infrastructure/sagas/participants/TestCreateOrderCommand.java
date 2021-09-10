package ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestCreateOrderCommand extends OrderCommand {
    public TestCreateOrderCommand(long orderId) {
        super(orderId);
        log.info("TestCreateOrderCommand created");
    }
}