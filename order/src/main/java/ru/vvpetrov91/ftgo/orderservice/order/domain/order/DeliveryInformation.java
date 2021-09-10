package ru.vvpetrov91.ftgo.orderservice.order.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryInformation {
    private LocalDateTime deliveryTime;
    private String deliveryAddress;
}