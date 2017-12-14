package com.github.rahmnathan.retail.price.data.data;

import org.springframework.data.annotation.Id;

public class ProductPrice {

    @Id
    private Long id;
    private Double price;
    private String currencyCode;

    public ProductPrice(Long id, Double price, String currencyCode) {
        this.id = id;
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public ProductPrice() {
    }

    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "id=" + id +
                ", price=" + price +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
