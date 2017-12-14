package com.github.rahmnathan.retail.product.info.web;

import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.price.data.exception.InvalidProductPriceException;
import com.github.rahmnathan.retail.product.info.boundary.ProductInfoFacade;
import com.github.rahmnathan.retail.product.info.data.ProductInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.logging.Level;
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

    @RequestMapping(value = "/products", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity putProductInfo(@RequestBody ProductPrice productPrice){
        logger.info("Received request to store ProductInfo: " + productPrice);

        try {
            productInfoFacade.upsertProductPrice(productPrice);
        } catch (InvalidProductPriceException e){
            logger.log(Level.INFO,"Error storing ProductPrice", e);
            return ResponseEntity.badRequest().body("Invalid product price: " + e.getMessage());
        }

        return ResponseEntity.ok("Successfully stored product price");
    }
}
