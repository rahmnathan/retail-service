package com.github.rahmnathan.retail.product.info.boundary;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.price.data.exception.InvalidProductPriceException;
import com.github.rahmnathan.retail.product.info.control.ProductInfoService;
import com.github.rahmnathan.retail.product.info.data.ProductInfo;
import com.github.rahmnathan.retail.product.info.exception.ProductInfoServiceException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ProductInfoFacade {
    private final Logger logger = Logger.getLogger(ProductInfo.class.getName());
    private final ProductInfoService productInfoService;
    private final LoadingCache<Long, ProductInfo> productInfoCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(15, TimeUnit.SECONDS)
            .build(this::getProductInfoFromProvider);

    @Inject
    public ProductInfoFacade(ProductInfoService productInfoService) {
        this.productInfoService = productInfoService;
    }

    public Optional<ProductInfo> getProductInfo(Long id) throws ProductInfoServiceException {
        try {
            return Optional.ofNullable(productInfoCache.get(id));
        } catch (CompletionException e) {
            logger.log(Level.INFO, "Error loading ProductInfo from cache", e);

            throw new ProductInfoServiceException("Error loading ProductInfo from cache", e.getCause());
        }
    }

    private ProductInfo getProductInfoFromProvider(Long id) throws ProductInfoServiceException {
        logger.info("Cache miss for id: " + id);
        return productInfoService.getProductInfo(id).orElse(null);
    }

    public void upsertProductPrice(ProductPrice productPrice) throws InvalidProductPriceException {
        productInfoService.upsertProductPrice(productPrice);
    }
}
