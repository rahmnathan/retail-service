package com.github.rahmnathan.retail.product.info.control;

import com.github.rahmnathan.retail.price.data.boundary.ProductPriceService;
import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.product.info.data.Price;
import com.github.rahmnathan.retail.product.info.data.ProductInfo;
import com.github.rahmnathan.retail.redsky.data.boundary.RedSkyProductService;
import com.github.rahmnathan.retail.redsky.data.data.RedSkyProduct;
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

    public ProductInfo getProductInfo(Long id) {
        if (id == null)
            throw new IllegalArgumentException("ProductInfo Id cannot be null");

        Optional<ProductPrice> productPriceOptional = productPriceService.getProductPrice(id);
        Optional<RedSkyProduct> redSkyProduct = redSkyProductService.getRedSkyProduct(id);

        Price price = productPriceOptional.map(productPrice ->
                new Price(productPrice.getPrice(), productPrice.getCurrencyCode()))
                .orElse(new Price(null, null));

        return new ProductInfo(id, redSkyProduct.map(RedSkyProduct::getName).orElse(null), price);
    }

    public void upsertProductPrice(ProductPrice productPrice){
        productPriceService.upsertProductPrice(productPrice);
    }
}
