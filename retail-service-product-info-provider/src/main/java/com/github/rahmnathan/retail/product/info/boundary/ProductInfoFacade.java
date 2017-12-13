package com.github.rahmnathan.retail.product.info.boundary;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.rahmnathan.retail.product.info.control.ProductInfoProvider;
import com.github.rahmnathan.retail.product.info.data.ProductInfo;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class ProductInfoFacade {
    private final Logger logger = Logger.getLogger(ProductInfo.class.getName());
    private final ProductInfoProvider productInfoProvider;
    private final LoadingCache<Long, ProductInfo> productInfoCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build(this::getProductInfo);

    @Inject
    public ProductInfoFacade(ProductInfoProvider productInfoProvider) {
        this.productInfoProvider = productInfoProvider;
    }

    public ProductInfo getProductInfo(Long Id){
        logger.info("Cache miss for id: " + Id);

        Optional<ProductInfo> productInfoOptional = productInfoProvider.getProductInfo(Id);
        if(productInfoOptional.isPresent()) {
            return productInfoOptional.get();
        }

        return null;
    }
}
