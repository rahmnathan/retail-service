package com.github.rahmnathan.retail.product.info.web;

import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.price.data.exception.InvalidProductPriceException;
import com.github.rahmnathan.retail.product.info.boundary.ProductInfoFacade;
import com.github.rahmnathan.retail.product.info.data.ProductInfo;
import com.github.rahmnathan.retail.product.info.exception.ProductInfoServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Optional;
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
    public ResponseEntity getProductInfo(@PathVariable Long productId){
        logger.info("Received request for ProductInfo. Id: " + productId);
        if(productId == null)
            return ResponseEntity.badRequest().body("Product ID cannot be null");

        try {
            Optional<ProductInfo> productInfo =  productInfoFacade.getProductInfo(productId);
            if(!productInfo.isPresent())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductInfo not found for id: " + productId);

            return ResponseEntity.status(200).body(productInfo);
        } catch (ProductInfoServiceException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
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
