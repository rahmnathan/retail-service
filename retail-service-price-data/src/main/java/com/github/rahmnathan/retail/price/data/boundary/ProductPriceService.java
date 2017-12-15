package com.github.rahmnathan.retail.price.data.boundary;

import com.github.rahmnathan.retail.price.data.api.IProductPriceService;
import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.price.data.exception.InvalidProductPriceException;
import com.github.rahmnathan.retail.price.data.persistence.ProductPriceEntity;
import com.github.rahmnathan.retail.price.data.persistence.ProductPriceRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ProductPriceService implements IProductPriceService {
    private final Logger logger = Logger.getLogger(ProductPriceService.class.getName());
    private final ProductPriceRepository productPriceRepository;

    @Inject
    public ProductPriceService(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }

    public void updateOrInsertProductPrice(ProductPrice productPrice) throws InvalidProductPriceException {
        validateParams(productPrice);

        if(getProductPrice(productPrice.getId()).isPresent()){
            logger.info("ProductPrice with id: " + productPrice.getId() + " already exists. Deleting it before insert");
            productPriceRepository.delete(productPrice.getId());
        }

        logger.info("Inserting ProductPrice: " + productPrice.toString());
        productPriceRepository.insert(domainObjectToEntity(productPrice));
    }

    public Optional<ProductPrice> getProductPrice(Long id) {
        ProductPriceEntity productPrice = productPriceRepository.findOne(id);
        logger.info("Query for ProductPrice: " + id + " returned: " + productPrice);

        return productPrice != null ? Optional.of(entityToDomainObject(productPrice)) : Optional.empty();
    }

    ProductPrice entityToDomainObject(ProductPriceEntity productPriceEntity){
        return new ProductPrice(productPriceEntity.getId(), productPriceEntity.getPrice(), productPriceEntity.getCurrencyCode());
    }

    ProductPriceEntity domainObjectToEntity(ProductPrice productPrice){
        return new ProductPriceEntity(productPrice.getId(), productPrice.getPrice(), productPrice.getCurrencyCode());
    }
}
