package ru.vvpetrov91.ftgo.orderservice.order.domain.order;

public enum OrderState {
    APPROVAL_PENDING,
    APPROVED,
    REJECTED,
    CANCEL_PENDING,
    CANCELLED,
    REVISION_PENDING,
}