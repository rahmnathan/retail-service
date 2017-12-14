package com.github.rahmnathan.retail.product.info.data;

import com.github.rahmnathan.retail.price.data.data.CurrencyCode;

public class Price {
    private final Double amount;
    private final CurrencyCode currency;

    public Price(Double amount, CurrencyCode currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }
}
