package com.github.rahmnathan.retail.redsky.data.boundary;

import com.github.rahmnathan.retail.redsky.data.control.RedSkyProductMapper;
import com.github.rahmnathan.retail.redsky.data.data.RedSkyProduct;
import com.github.rahmnathan.retail.redsky.data.exception.RedSkyServiceException;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class RedSkyProductService {
    private final Logger logger = Logger.getLogger(RedSkyProductService.class.getName());
    private final String params = "excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_" +
            "review_statistics,question_answer_statistics";
    private final String basePath = "/v2/pdp/tcin/";
    private final ProducerTemplate producerTemplate;

    @Inject
    public RedSkyProductService(ProducerTemplate producerTemplate){
        this.producerTemplate = producerTemplate;
    }

    public Optional<RedSkyProduct> getRedSkyProduct(Long id) throws RedSkyServiceException {
        Map<String, Object> headers = buildHeaders(id);
        logger.info("Requesting RedSkyProduct: " + id + " with the following headers: " + headers.toString());

        Message responseMessage = producerTemplate.request("direct:productinfo",
                exchange -> exchange.getIn().setHeaders(headers))
                .getOut();

        return parseResponse(responseMessage, id);
    }

    private Map<String, Object> buildHeaders(Long id){
        Map<String, Object> headers = new HashMap<>();
        headers.put(Exchange.HTTP_PATH, basePath + id);
        headers.put(Exchange.HTTP_QUERY, params);

        return headers;
    }

    private Optional<RedSkyProduct> parseResponse(Message responseMessage, Long id) throws RedSkyServiceException {
        Integer responseCode = responseMessage.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
        String responseBody = responseMessage.getBody(String.class);

        logger.info("Response for: " + id + " Code: " + responseCode + " Message: " + responseBody);
        logger.fine("Response for: " + id + " Message: " + responseBody);

        if(responseCode == null || responseBody == null || responseCode == 404){
            return Optional.empty();
        } else if (responseCode != 200){
            throw new RedSkyServiceException("Failure calling RedSky. Response Code: " + responseCode + " Response Message: " + responseBody);
        }

        return Optional.of(RedSkyProductMapper.buildRedSkyProduct(responseBody));
    }
}
