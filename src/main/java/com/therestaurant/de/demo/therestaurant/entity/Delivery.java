package com.therestaurant.de.demo.therestaurant.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="deliveries")
@Data
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Timestamp startTime;

    private Timestamp deliveredTime;

    public enum DeliveryStatus {
        Processing,
        ForDelivery,
        Delivered,
        Canceled
    }

    private DeliveryStatus status;

    public Delivery(Order order) {
        this.order = order;
        this.status = DeliveryStatus.Processing;
    }
}
