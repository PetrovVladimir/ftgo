package ru.vvpetrov91.ftgo.orderservice.order.service;

public class InvalidMenuItemIdException extends RuntimeException{
    public InvalidMenuItemIdException(Long menuItemId) {
        super("Invalid menu item id " + menuItemId);
    }
}