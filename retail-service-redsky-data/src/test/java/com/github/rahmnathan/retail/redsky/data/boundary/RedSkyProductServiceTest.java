package com.github.rahmnathan.retail.redsky.data.boundary;

import com.github.rahmnathan.retail.redsky.data.control.TestResourceLoader;
import com.github.rahmnathan.retail.redsky.data.exception.RedSkyServiceException;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RedSkyProductServiceTest {
    private final Long testId = 1L;
    private final Message mockMessage = mock(Message.class);
    private final Exchange mockExchange = mock(Exchange.class);
    private final ProducerTemplate mockProducerTemplate = mock(ProducerTemplate.class);

    @Test
    public void getValidRedSkyProductTest() throws IOException, RedSkyServiceException {
        String fileContents = TestResourceLoader.loadStringFromFile("src/test/resources/example-response.json");
        when(mockMessage.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class)).thenReturn(200);
        when(mockMessage.getBody(String.class)).thenReturn(fileContents);
        when(mockExchange.getOut()).thenReturn(mockMessage);
        when(mockProducerTemplate.request(any(String.class), any())).thenReturn(mockExchange);

        RedSkyProductService redSkyProductService = new RedSkyProductService(mockProducerTemplate);
        Assert.assertTrue(redSkyProductService.getRedSkyProduct(testId).isPresent());
    }

    @Test
    public void getMissingRedSkyProductTest() throws RedSkyServiceException {
        when(mockMessage.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class)).thenReturn(404);
        when(mockMessage.getBody(String.class)).thenReturn(null);
        when(mockExchange.getOut()).thenReturn(mockMessage);
        when(mockProducerTemplate.request(any(String.class), any())).thenReturn(mockExchange);

        RedSkyProductService redSkyProductService = new RedSkyProductService(mockProducerTemplate);
        Assert.assertFalse(redSkyProductService.getRedSkyProduct(testId).isPresent());
    }

    @Test(expected = RedSkyServiceException.class)
    public void failingRedSkyServiceTest() throws RedSkyServiceException {
        when(mockMessage.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class)).thenReturn(500);
        when(mockMessage.getBody(String.class)).thenReturn("");
        when(mockExchange.getOut()).thenReturn(mockMessage);
        when(mockProducerTemplate.request(any(String.class), any())).thenReturn(mockExchange);

        RedSkyProductService redSkyProductService = new RedSkyProductService(mockProducerTemplate);
        redSkyProductService.getRedSkyProduct(testId);
    }
}
