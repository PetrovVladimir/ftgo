package ru.vvpetrov91.ftgo.orderservice.api.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponse {
    private long orderId;
}