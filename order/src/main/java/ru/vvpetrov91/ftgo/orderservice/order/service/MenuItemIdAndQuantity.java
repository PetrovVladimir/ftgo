package ru.vvpetrov91.ftgo.orderservice.order.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuItemIdAndQuantity {
    private Long menuItemId;
    private int quantity;
}
