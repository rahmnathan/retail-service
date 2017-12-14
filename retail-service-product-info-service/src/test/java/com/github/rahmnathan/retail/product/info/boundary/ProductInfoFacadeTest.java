package com.github.rahmnathan.retail.product.info.boundary;

import com.github.rahmnathan.retail.price.data.api.IProductPriceService;
import com.github.rahmnathan.retail.price.data.data.CurrencyCode;
import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.product.info.control.ProductInfoService;
import com.github.rahmnathan.retail.product.info.exception.ProductInfoServiceException;
import com.github.rahmnathan.retail.redsky.data.api.IRedSkyProductService;
import com.github.rahmnathan.retail.redsky.data.data.RedSkyProduct;
import com.github.rahmnathan.retail.redsky.data.exception.RedSkyServiceException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductInfoFacadeTest {
    private static final Long productInfoId = 1L;
    private static final IRedSkyProductService mockRedSkyProductService = mock(IRedSkyProductService.class);
    private static final IProductPriceService mockProductPriceService = mock(IProductPriceService.class);

    @Test(expected = ProductInfoServiceException.class)
    public void getProductInfoRedSkyExceptionTest() throws Exception {
        when(mockRedSkyProductService.getRedSkyProduct(productInfoId)).thenThrow(RedSkyServiceException.class);
        when(mockProductPriceService.getProductPrice(productInfoId)).thenReturn(Optional.of(mock(ProductPrice.class)));

        ProductInfoService infoService = new ProductInfoService(mockRedSkyProductService, mockProductPriceService);
        ProductInfoFacade productInfoFacade = new ProductInfoFacade(infoService);

        productInfoFacade.getProductInfo(productInfoId);
    }

    @Test
    public void getProductInfoMissingPriceTest() throws Exception {
        when(mockProductPriceService.getProductPrice(productInfoId)).thenReturn(Optional.empty());

        ProductInfoService infoService = new ProductInfoService(mockRedSkyProductService, mockProductPriceService);
        ProductInfoFacade productInfoFacade = new ProductInfoFacade(infoService);

        Assert.assertFalse(productInfoFacade.getProductInfo(productInfoId).isPresent());
    }

    @Test
    public void getProductInfoMissingRedSkyProductTest() throws Exception {
        when(mockProductPriceService.getProductPrice(productInfoId)).thenReturn(Optional.of(mock(ProductPrice.class)));
        when(mockRedSkyProductService.getRedSkyProduct(productInfoId)).thenReturn(Optional.empty());

        ProductInfoService infoService = new ProductInfoService(mockRedSkyProductService, mockProductPriceService);
        ProductInfoFacade productInfoFacade = new ProductInfoFacade(infoService);

        Assert.assertFalse(productInfoFacade.getProductInfo(productInfoId).isPresent());
    }

    @Test
    public void getProductInfoTest() throws Exception {
        Optional<ProductPrice> productPrice = Optional.of(new ProductPrice(productInfoId, 2.5, CurrencyCode.USD));
        when(mockProductPriceService.getProductPrice(productInfoId)).thenReturn(productPrice);

        Optional<RedSkyProduct> redSkyProduct = Optional.of(new RedSkyProduct("TestName"));
        when(mockRedSkyProductService.getRedSkyProduct(productInfoId)).thenReturn(redSkyProduct);

        ProductInfoService infoService = new ProductInfoService(mockRedSkyProductService, mockProductPriceService);
        ProductInfoFacade productInfoFacade = new ProductInfoFacade(infoService);

        Assert.assertTrue(productInfoFacade.getProductInfo(productInfoId).isPresent());
    }
}
