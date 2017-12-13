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

    public Optional<ProductInfo> getProductInfo(Long id) {
        if (id == null)
            throw new IllegalArgumentException("ProductInfo Id cannot be null");

        Optional<ProductPrice> productPrice = productPriceService.getProductPrice(id);
        Optional<RedSkyProduct> productInfo = redSkyProductService.getRedSkyProduct(id);

        return buildProductInfo(productPrice, productInfo, id);
    }

    public void upsertProductPrice(ProductPrice productPrice){
        productPriceService.upsertProductPrice(productPrice);
    }

    private Optional<ProductInfo> buildProductInfo(Optional<ProductPrice> productPrice, Optional<RedSkyProduct> redSkyProduct, Long id){
        if(productPrice.isPresent() && redSkyProduct.isPresent()){
            ProductPrice productPrice1 = productPrice.get();
            Price price = new Price(productPrice1.getPrice(), productPrice1.getCurrency().getCurrencyCode());
            return Optional.of(new ProductInfo(id, redSkyProduct.get().getName(), price));
        }


        return Optional.empty();
    }
}
