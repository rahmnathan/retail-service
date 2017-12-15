package com.github.rahmnathan.retail.price.data.api;

import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.price.data.exception.InvalidProductPriceException;

import java.util.Optional;

public interface IProductPriceService {

        Optional<ProductPrice> getProductPrice(Long id);

        void upsertProductPrice(ProductPrice productPrice) throws InvalidProductPriceException;

        default void validateParams(ProductPrice productPrice) throws InvalidProductPriceException {
                if (productPrice == null || productPrice.getId() == null ||
                        productPrice.getPrice() == null || productPrice.getCurrencyCode() == null) {

                        throw new InvalidProductPriceException("Invalid ProductPrice: " + productPrice);
                }
        }
}