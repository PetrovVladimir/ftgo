package ru.vvpetrov91.ftgo.orderservice.order.domain.order;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class PaymentInformation {
    private String paymentToken;
}