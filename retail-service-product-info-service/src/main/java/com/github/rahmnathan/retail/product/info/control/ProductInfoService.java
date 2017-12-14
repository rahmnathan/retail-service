package com.github.rahmnathan.retail.product.info.control;

import com.github.rahmnathan.retail.price.data.boundary.ProductPriceService;
import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.product.info.data.Price;
import com.github.rahmnathan.retail.product.info.data.ProductInfo;
import com.github.rahmnathan.retail.price.data.exception.InvalidProductPriceException;
import com.github.rahmnathan.retail.product.info.exception.ProductInfoServiceException;
import com.github.rahmnathan.retail.redsky.data.boundary.RedSkyProductService;
import com.github.rahmnathan.retail.redsky.data.data.RedSkyProduct;
import com.github.rahmnathan.retail.redsky.data.exception.RedSkyServiceException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;

@Component
public class ProductInfoService {
    private final RedSkyProductService redSkyProductService;
    private final ProductPriceService productPriceService;

    @Inject
    public ProductInfoService(RedSkyProductService redSkyProductService, ProductPriceService productPriceService) {
        this.redSkyProductService = redSkyProductService;
        this.productPriceService = productPriceService;
    }

    public ProductInfo getProductInfo(Long id) throws RedSkyServiceException, ProductInfoServiceException {
        if (id == null)
            throw new IllegalArgumentException("ProductInfo Id cannot be null");

        Optional<ProductPrice> productPriceOptional = productPriceService.getProductPrice(id);
        if(!productPriceOptional.isPresent()){
            throw new ProductInfoServiceException("Failed to find ProductPrice for id: " + id);
        }

        Optional<RedSkyProduct> redSkyProduct = redSkyProductService.getRedSkyProduct(id);
        if(!redSkyProduct.isPresent()){
            throw new ProductInfoServiceException("Failed to find RedSkyProduct for id: " + id);
        }

        ProductPrice productPrice = productPriceOptional.get();
        Price price = new Price(productPrice.getPrice(), productPrice.getCurrencyCode());
        return new ProductInfo(id, redSkyProduct.get().getName(), price);
    }

    public void upsertProductPrice(ProductPrice productPrice) throws InvalidProductPriceException {
        productPriceService.upsertProductPrice(productPrice);
    }
}
