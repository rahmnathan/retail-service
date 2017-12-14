package com.github.rahmnathan.retail.product.info.control;

import com.github.rahmnathan.retail.price.data.api.IProductPriceService;
import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.product.info.data.Price;
import com.github.rahmnathan.retail.product.info.data.ProductInfo;
import com.github.rahmnathan.retail.price.data.exception.InvalidProductPriceException;
import com.github.rahmnathan.retail.product.info.exception.ProductInfoServiceException;
import com.github.rahmnathan.retail.redsky.data.api.IRedSkyProductService;
import com.github.rahmnathan.retail.redsky.data.data.RedSkyProduct;
import com.github.rahmnathan.retail.redsky.data.exception.RedSkyServiceException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;

@Component
public class ProductInfoService {
    private final IRedSkyProductService redSkyProductService;
    private final IProductPriceService productPriceService;

    @Inject
    public ProductInfoService(IRedSkyProductService redSkyProductService, IProductPriceService productPriceService) {
        this.redSkyProductService = redSkyProductService;
        this.productPriceService = productPriceService;
    }

    public Optional<ProductInfo> getProductInfo(Long id) throws ProductInfoServiceException {
        if (id == null)
            throw new IllegalArgumentException("ProductInfo Id cannot be null");

        Optional<ProductPrice> productPriceOptional = productPriceService.getProductPrice(id);
        if(!productPriceOptional.isPresent())
            return Optional.empty();

        Optional<RedSkyProduct> redSkyProduct = getRedSkyProduct(id);
        if(!redSkyProduct.isPresent())
            return Optional.empty();

        ProductPrice productPrice = productPriceOptional.get();
        Price price = new Price(productPrice.getPrice(), productPrice.getCurrencyCode());
        return Optional.of(new ProductInfo(id, redSkyProduct.get().getName(), price));
    }

    public void upsertProductPrice(ProductPrice productPrice) throws InvalidProductPriceException {
        productPriceService.upsertProductPrice(productPrice);
    }

    private Optional<RedSkyProduct> getRedSkyProduct(Long id) throws ProductInfoServiceException {
        try {
            return redSkyProductService.getRedSkyProduct(id);
        } catch (RedSkyServiceException e){
            throw new ProductInfoServiceException("Failed to get red sky product", e);
        }
    }
}
