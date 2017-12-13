package com.github.rahmnathan.retail.price.data.boundary;

import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.price.data.persistence.ProductPriceRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;

@Component
public class ProductPriceService {
    private final ProductPriceRepository productPriceRepository;

    @Inject
    public ProductPriceService(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }

    public void upsertProductPrice(ProductPrice productPrice){
        validateParams(productPrice);

        productPriceRepository.insert(productPrice);
    }

    public Optional<ProductPrice> getProductPrice(Long Id){
        return Optional.ofNullable(productPriceRepository.findOne(Id));
    }

    private void validateParams(ProductPrice productPrice) {
        if(productPrice == null || productPrice.getId() == null ||
                productPrice.getPrice() == null || productPrice.getCurrency() == null) {

            throw new IllegalArgumentException("Invalid ProductPrice: " + productPrice);
        }
    }
}
