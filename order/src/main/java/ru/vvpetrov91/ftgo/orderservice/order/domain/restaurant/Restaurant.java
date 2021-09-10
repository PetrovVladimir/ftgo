package ru.vvpetrov91.ftgo.orderservice.order.domain.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "order_service_restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    private Long id;

    @ElementCollection
    @CollectionTable(name = "order_service_restaurant_menu_items")
    private List<MenuItem> restaurantMenuItems;
    private String name;

    public Optional<MenuItem> findMenuItem(Long menuItemId) {
        return restaurantMenuItems.stream().filter(mi -> mi.getMenuItemId().equals(menuItemId)).findFirst();
    }
}
