package com.github.rahmnathan.retail.product.info.data;

public class ProductInfo {
    private final Long id;
    private final String name;
    private final Price currentPrice;

    public ProductInfo(Long id, String name, Price currentPrice) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getCurrentPrice() {
        return currentPrice;
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
