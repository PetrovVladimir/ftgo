package ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApproveOrderCommand extends OrderCommand {
    public ApproveOrderCommand(long orderId) {
        super(orderId);
    }
}