package com.github.rahmnathan.retail.redsky.data.control;

import com.github.rahmnathan.retail.redsky.data.data.RedSkyProduct;
import com.github.rahmnathan.retail.redsky.data.exception.RedSkyServiceException;
import org.json.JSONObject;

import java.util.Optional;

public class RedSkyProductMapper {

    public static RedSkyProduct buildRedSkyProduct(String jsonString) throws RedSkyServiceException {
        JSONObject fullObject = new JSONObject(jsonString);
        if(fullObject.has("product")){
            JSONObject product = fullObject.getJSONObject("product");
            if(product.has("item")){
                JSONObject item = product.getJSONObject("item");
                if(item.has("product_description")){
                    JSONObject productDescription = item.getJSONObject("product_description");

                    if(productDescription.has("title")){
                        String title = productDescription.getString("title");
                        return new RedSkyProduct(title);
                    }
                }
            }
        }

        throw new RedSkyServiceException("Failure mapping response to RedSkyProduct");
    }
}
