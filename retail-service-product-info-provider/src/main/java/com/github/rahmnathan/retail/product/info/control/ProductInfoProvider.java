package com.github.rahmnathan.retail.product.info.control;

import com.github.rahmnathan.retail.price.data.boundary.ProductPriceProvider;
import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.product.info.data.Price;
import com.github.rahmnathan.retail.product.info.data.ProductInfo;
import com.github.rahmnathan.retail.redsky.data.boundary.RedSkyProductProvider;
import com.github.rahmnathan.retail.redsky.data.data.RedSkyProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;

@Component
public class ProductInfoProvider {
    private final RedSkyProductProvider redSkyProductProvider;
    private final ProductPriceProvider productPriceProvider;

    @Inject
    public ProductInfoProvider(RedSkyProductProvider redSkyProductProvider, ProductPriceProvider productPriceProvider) {
        this.redSkyProductProvider = redSkyProductProvider;
        this.productPriceProvider = productPriceProvider;
    }

    public Optional<ProductInfo> getProductInfo(Long id) {
        if (id == null)
            throw new IllegalArgumentException("ProductInfo Id cannot be null");

        Optional<ProductPrice> productPrice = productPriceProvider.getProductPrice(id);
        Optional<RedSkyProduct> productInfo = redSkyProductProvider.getRedSkyProduct(id);

        return buildProductInfo(productPrice, productInfo, id);
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
