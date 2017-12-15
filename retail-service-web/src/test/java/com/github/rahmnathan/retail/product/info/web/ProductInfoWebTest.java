package com.github.rahmnathan.retail.product.info.web;

import com.github.rahmnathan.retail.price.data.data.CurrencyCode;
import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.price.data.exception.InvalidProductPriceException;
import com.github.rahmnathan.retail.product.info.boundary.ProductInfoFacade;
import com.github.rahmnathan.retail.product.info.control.ProductInfoService;
import com.github.rahmnathan.retail.product.info.data.ProductInfo;
import com.github.rahmnathan.retail.product.info.exception.ProductInfoServiceException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductInfoWebTest {
    private static final Long validId = 1L;
    private static final Long invalidId = 2L;
    private static ProductInfoWeb productInfoWeb;

    @BeforeClass
    public static void initialize() throws ProductInfoServiceException {
        ProductInfoService productInfoService = mock(ProductInfoService.class);
        when(productInfoService.getProductInfo(validId)).thenReturn(Optional.of(mock(ProductInfo.class)));
        when(productInfoService.getProductInfo(invalidId)).thenReturn(Optional.empty());

        ProductInfoFacade productInfoFacade = new ProductInfoFacade(productInfoService);
        productInfoWeb = new ProductInfoWeb(productInfoFacade);
    }

    @Test
    public void getInvalidProductInfoTest(){
        Assert.assertEquals(404, productInfoWeb.getProductInfo(invalidId).getStatusCodeValue());
    }

    @Test
    public void getValidProductInfoTest(){
        Assert.assertEquals(200, productInfoWeb.getProductInfo(validId).getStatusCodeValue());
    }

    @Test
    public void upsertValidProductPriceTest(){
        ProductPrice validProductPrice = new ProductPrice(validId, 2.5, CurrencyCode.USD);
        Assert.assertEquals(200, productInfoWeb.putProductPrice(validProductPrice).getStatusCodeValue());
    }
}
