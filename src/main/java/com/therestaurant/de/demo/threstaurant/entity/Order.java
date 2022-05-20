package com.therestaurant.de.demo.threstaurant.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name="orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String customer;
    private float totalAmount;

    private Timestamp orderDate;

    private String paymentId;

    private String paymentStatus;

    private String address;

    public enum OrderStatus {
        Canceled,
        Processing,
        ForDelivery,
        Completed
    }

    @Enumerated
    @NotNull
    private OrderStatus status;

    public Order(String customer, float totalAmount, String address, String paymentId, String paymentStatus, OrderStatus status) {
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.address = address;
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
        this.status = status;

        this.orderDate = new Timestamp(System.currentTimeMillis());
    }
}
