package com.github.rahmnathan.retail.price.data.data;

public class ProductPrice {

    private Long id;
    private Double price;
    private CurrencyCode currencyCode;

    public ProductPrice(Long id, Double price, CurrencyCode currencyCode) {
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

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "id=" + id +
                ", price=" + price +
                ", currencyCode=" + currencyCode +
                '}';
    }
}
