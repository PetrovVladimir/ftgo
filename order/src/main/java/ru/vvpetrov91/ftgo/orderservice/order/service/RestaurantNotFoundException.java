package ru.vvpetrov91.ftgo.orderservice.order.service;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(long restaurantId) {
        super("Restaurant not found with id " + restaurantId);
    }
}