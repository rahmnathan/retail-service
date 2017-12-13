package com.github.rahmnathan.retail.product.info.data;

public class ProductInfo {
    private Long id;
    private String name;
    private Price currentPrice;

    public ProductInfo(Long id, String name, Price currentPrice) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public Long getId() {
        return id;
    }

    public ProductInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductInfo setName(String name) {
        this.name = name;
        return this;
    }

    public Price getCurrentPrice() {
        return currentPrice;
    }

    public ProductInfo setCurrentPrice(Price currentPrice) {
        this.currentPrice = currentPrice;
        return this;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
