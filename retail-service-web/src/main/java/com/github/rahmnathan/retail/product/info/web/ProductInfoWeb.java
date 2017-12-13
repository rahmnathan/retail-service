package com.github.rahmnathan.retail.product.info.web;

import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.product.info.boundary.ProductInfoFacade;
import com.github.rahmnathan.retail.product.info.data.ProductInfo;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.logging.Logger;

@RestController
public class ProductInfoWeb {
    private final Logger logger = Logger.getLogger(ProductInfoWeb.class.getName());
    private final ProductInfoFacade productInfoFacade;

    @Inject
    public ProductInfoWeb(ProductInfoFacade productInfoFacade) {
        this.productInfoFacade = productInfoFacade;
    }

    @RequestMapping(value = "/products/{productId}", produces = "application/json", method = RequestMethod.GET)
    public ProductInfo getProductInfo(@PathVariable Long productId){
        logger.info("Received request for ProductInfo. Id: " + productId);

        return productInfoFacade.getProductInfo(productId);
    }

    @RequestMapping(value = "/products", produces = "application/json", method = RequestMethod.PUT)
    public void putProductInfo(@RequestBody ProductPrice productPrice){
        logger.info("Received request to store ProductInfo: " + productPrice);
        productInfoFacade.upsertProductPrice(productPrice);
    }
}
