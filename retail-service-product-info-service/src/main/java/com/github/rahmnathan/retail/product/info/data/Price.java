package com.github.rahmnathan.retail.product.info.data;

public class Price {
    private final Double amount;
    private final String currency;

    public Price(Double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
