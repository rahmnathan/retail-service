package com.github.rahmnathan.retail.redsky.data.boundary;

import com.github.rahmnathan.retail.redsky.data.control.RedSkyProductMapper;
import com.github.rahmnathan.retail.redsky.data.data.RedSkyProduct;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class RedSkyProductService {
    private final String params = "excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_" +
            "review_statistics,question_answer_statistics";
    private final String basePath = "/v2/pdp/tcin/";
    private final ProducerTemplate producerTemplate;

    @Inject
    public RedSkyProductService(ProducerTemplate producerTemplate){
        this.producerTemplate = producerTemplate;
    }

    public Optional<RedSkyProduct> getRedSkyProduct(Long id){
        Map<String, Object> headers = buildHeaders(id);

        Message responseMessage = producerTemplate.request("direct:productinfo",
                exchange -> exchange.getIn().setHeaders(headers))
                .getOut();

        Integer responseCode = responseMessage.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
        String responseText = responseMessage.getHeader(Exchange.HTTP_RESPONSE_TEXT, String.class);

        return processResponse(responseCode, responseText);
    }

    private Optional<RedSkyProduct> processResponse(Integer responseCode, String responseText){
        if(responseCode == null || responseText == null || responseCode == 404){
            return Optional.empty();
        } else if (responseCode != 200){
            throw new RuntimeException("Failure calling RedSky. Response Code: " + responseCode + " Response Message: " + responseText);
        }

        return Optional.of(RedSkyProductMapper.buildRedSkyProduct(responseText));
    }

    private Map<String, Object> buildHeaders(Long id){
        Map<String, Object> headers = new HashMap<>();
        headers.put(Exchange.HTTP_PATH, basePath + id);
        headers.put(Exchange.HTTP_QUERY, params);

        return headers;
    }
}

