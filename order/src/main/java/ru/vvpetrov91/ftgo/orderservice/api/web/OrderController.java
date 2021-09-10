package ru.vvpetrov91.ftgo.orderservice.api.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vvpetrov91.ftgo.orderservice.order.domain.order.DeliveryInformation;
import ru.vvpetrov91.ftgo.orderservice.order.domain.order.Order;
import ru.vvpetrov91.ftgo.orderservice.order.service.MenuItemIdAndQuantity;
import ru.vvpetrov91.ftgo.orderservice.order.service.OrderService;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @PostMapping()
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        Order newOrder = orderService.createOrderBy(
                createOrderRequest.getConsumerId(),
                createOrderRequest.getRestaurantId(),
                new DeliveryInformation(createOrderRequest.getDeliveryTime(), createOrderRequest.getDeliveryAddress()),
                createOrderRequest.getLineItems().stream()
                        .map(lineItem -> new MenuItemIdAndQuantity(lineItem.getMenuItemId(), lineItem.getQuantity()))
                        .collect(toList())
        );

        return new CreateOrderResponse(newOrder.getId());
    }
}