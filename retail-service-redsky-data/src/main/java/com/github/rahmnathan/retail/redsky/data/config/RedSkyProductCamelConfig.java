package com.github.rahmnathan.retail.redsky.data.config;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class RedSkyProductCamelConfig {

    @Value(value = "${product.info.host}")
    private String productInfoHost;
    private final Logger logger = Logger.getLogger(RedSkyProductCamelConfig.class.getName());
    private final CamelContext camelContext;

    @Inject
    public RedSkyProductCamelConfig(CamelContext camelContext){
        this.camelContext = camelContext;
    }

    @PostConstruct
    public void configureCamelRoutes(){
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {

                    onException(HttpOperationFailedException.class)
                            .useExponentialBackOff()
                            .backOffMultiplier(2)
                            .redeliveryDelay(500)
                            .maximumRedeliveries(2)
                            .onWhen(exchange -> exchange.getProperty(Exchange.EXCEPTION_CAUGHT, HttpOperationFailedException.class).getStatusCode() != 404)
                            .end();

                    from("direct:productinfo")
                            .to("http4://" + productInfoHost)
                            .end();
                }
            });
        } catch (Exception e){
            logger.log(Level.SEVERE, "Failure adding routes to Camel context", e);
        }
    }
}
