package ru.vvpetrov91.ftgo.orderservice.order.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

import static java.util.Objects.nonNull;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItem {
    private Long menuItemId;
    private String name;
    private BigDecimal price;
    private int quantity;

    @Transient
    public BigDecimal getTotalPrice() {
        return nonNull(price) ? price.multiply(new BigDecimal(quantity)) : new BigDecimal(0);
    }
}