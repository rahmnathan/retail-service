package com.github.rahmnathan.retail.product.info.web;

import com.github.rahmnathan.retail.product.info.boundary.ProductInfoFacade;
import com.github.rahmnathan.retail.product.info.data.ProductInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.logging.Logger;

@RestController
public class ProductInfoService {
    private final Logger logger = Logger.getLogger(ProductInfoService.class.getName());
    private final ProductInfoFacade productInfoFacade;

    @Inject
    public ProductInfoService(ProductInfoFacade productInfoFacade) {
        this.productInfoFacade = productInfoFacade;
    }

    @RequestMapping(value = "/products/{productId}", produces = "application/json", method = RequestMethod.GET)
    public ProductInfo getProductInfo(@PathVariable Long productId){
        logger.info("Received request for ProductInfo. Id: " + productId);

        return productInfoFacade.getProductInfo(productId);
    }


}
