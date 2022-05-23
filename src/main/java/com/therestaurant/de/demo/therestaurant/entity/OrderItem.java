package com.therestaurant.de.demo.therestaurant.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="orderitems")
@Data
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String name;

    private float unitPrice;

    private Integer qty;

    private float amount;

    public OrderItem(Order order, String itemName, float unitPrice, Integer qty, float amount) {
        this.order = order;
        this.name = itemName;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.amount = amount;
    }
}
