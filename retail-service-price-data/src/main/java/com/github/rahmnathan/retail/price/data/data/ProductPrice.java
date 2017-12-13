package com.github.rahmnathan.retail.price.data.data;

import org.springframework.data.annotation.Id;

import java.util.Currency;

public class ProductPrice {

    @Id
    private Long id;
    private Double price;
    private Currency currency;

    public ProductPrice(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "id=" + id +
                ", price=" + price +
                ", currency=" + currency +
                '}';
    }
}
