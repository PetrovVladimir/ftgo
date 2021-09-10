package ru.vvpetrov91.ftgo.orderservice.order.infrastructure.sagas.participants;

import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class OrderCommand implements Command {
    private long orderId;
}