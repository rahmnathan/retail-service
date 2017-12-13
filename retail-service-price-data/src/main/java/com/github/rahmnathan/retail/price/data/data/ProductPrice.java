package com.github.rahmnathan.retail.price.data.data;

import org.springframework.data.annotation.Id;

import java.util.Currency;

public class ProductPrice {

    @Id
    private Long id;
    private Double price;
    private String currencyCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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
