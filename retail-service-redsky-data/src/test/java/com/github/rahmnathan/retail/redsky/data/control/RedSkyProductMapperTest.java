package com.github.rahmnathan.retail.redsky.data.control;

import com.github.rahmnathan.retail.redsky.data.exception.RedSkyServiceException;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

public class RedSkyProductMapperTest {

    @Test
    public void testValidResponseMapping() throws Exception {
        String exampleResponseText = TestResourceLoader.loadStringFromFile("src/test/resources/example-response.json");
        Assert.assertEquals("The Big Lebowski (Blu-ray)", RedSkyProductMapper.buildRedSkyProduct(exampleResponseText).getName());
    }

    @Test(expected = JSONException.class)
    public void testInvalidJSONMapping() throws RedSkyServiceException {
        RedSkyProductMapper.buildRedSkyProduct("{{}");
    }

    @Test(expected = RuntimeException.class)
    public void testValidJSONMissingRequiredFieldMapping() throws RedSkyServiceException {
        RedSkyProductMapper.buildRedSkyProduct("{\"test\": null");
    }
}
