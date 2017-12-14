package com.github.rahmnathan.retail.price.data.persistence;

import com.github.rahmnathan.retail.price.data.data.CurrencyCode;
import org.springframework.data.annotation.Id;

public class ProductPriceEntity {

    @Id
    private Long id;
    private Double price;
    private CurrencyCode currencyCode;

    public ProductPriceEntity(Long id, Double price, CurrencyCode currencyCode) {
        this.id = id;
        this.price = price;
        this.currencyCode = currencyCode;
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
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
