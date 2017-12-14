package com.github.rahmnathan.retail.price.data.boundary;

import com.github.rahmnathan.retail.price.data.data.ProductPrice;
import com.github.rahmnathan.retail.price.data.exception.InvalidProductPriceException;
import com.github.rahmnathan.retail.price.data.persistence.ProductPriceRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductPriceServiceTest {
    private static final Long invalidProductPriceId = 1L;
    private static ProductPrice invalidProductPrice;
    private static final Long validProductPriceId = 2L;
    private static ProductPrice validProductPrice;
    private static ProductPriceService priceService;

    @BeforeClass
    public static void initialize(){
        invalidProductPrice = new ProductPrice();
        invalidProductPrice.setId(invalidProductPriceId);

        ProductPriceRepository priceRepository = mock(ProductPriceRepository.class);
        when(priceRepository.findOne(invalidProductPriceId)).thenReturn(invalidProductPrice);
        when(priceRepository.findOne(validProductPriceId)).thenReturn(validProductPrice);

        priceService = new ProductPriceService(priceRepository);

        validProductPrice = new ProductPrice();
        validProductPrice.setId(validProductPriceId);
        validProductPrice.setCurrencyCode("USD");
        validProductPrice.setPrice(2.5);
    }

    @Test
    public void getExistingProductPriceTest(){
        Optional<ProductPrice> productPrice = priceService.getProductPrice(invalidProductPriceId);

        Assert.assertTrue(productPrice.isPresent());
    }

    @Test
    public void getMissingProductPriceTest(){
        Optional<ProductPrice> productPrice = priceService.getProductPrice(validProductPriceId);

        Assert.assertFalse(productPrice.isPresent());
    }

    @Test
    public void upsertValidProductPriceTest() throws InvalidProductPriceException {
        priceService.upsertProductPrice(validProductPrice);
    }

    @Test(expected = InvalidProductPriceException.class)
    public void upsertInvalidProductPriceTest() throws InvalidProductPriceException {
        priceService.upsertProductPrice(invalidProductPrice);
    }
}