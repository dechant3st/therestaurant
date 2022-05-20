package com.therestaurant.de.demo.threstaurant.dto;

import lombok.Data;

@Data
public class ChargeRequest {
    public enum Currency {
        EUR, USD;
    }

    private String description;
    private Integer amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;
    private String stripeTokenType;
}
