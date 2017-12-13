package com.github.rahmnathan.retail.price.data.boundary;

import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.price.data.persistence.ProductPriceRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ProductPriceService {
    private final Logger logger = Logger.getLogger(ProductPriceService.class.getName());
    private final ProductPriceRepository productPriceRepository;

    @Inject
    public ProductPriceService(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }

    public void upsertProductPrice(ProductPrice productPrice){
        validateParams(productPrice);

        if(getProductPrice(productPrice.getId()).isPresent()){
            logger.info("ProductPrice with id: " + productPrice.getId() + " already exists. Deleting it before insert");
            productPriceRepository.delete(productPrice.getId());
        }

        logger.info("Inserting ProductPrice: " + productPrice.toString());
        productPriceRepository.insert(productPrice);
    }

    public Optional<ProductPrice> getProductPrice(Long id){
        ProductPrice productPrice = productPriceRepository.findOne(id);

        logger.info("Query for ProductPrice: " + id + " returned: " + productPrice);
        return Optional.ofNullable(productPrice);
    }

    private void validateParams(ProductPrice productPrice) {
        if(productPrice == null || productPrice.getId() == null ||
                productPrice.getPrice() == null || productPrice.getCurrencyCode() == null) {

            throw new IllegalArgumentException("Invalid ProductPrice: " + productPrice);
        }
    }
}
