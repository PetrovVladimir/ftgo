package ru.vvpetrov91.ftgo.orderservice.order.domain.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    private Long menuItemId;
    private String name;
    private BigDecimal price;
}
