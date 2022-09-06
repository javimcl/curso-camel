package com.course.microservices.camelmicroserviceb.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

    @Autowired
    MyCurrencyExchangeProcess myCurrencyExchangeProcess;

    @Autowired
    MyCurrencyExchangeTransformer myCurrencyExchangeTransformer;

    @Override
    public void configure() throws Exception {
        from("activemq:my-activemq-queue")
                .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
                .bean(myCurrencyExchangeProcess)
                .bean(myCurrencyExchangeTransformer)
                .to("log:received-message-from-active-mq");

    }


}

@Slf4j
@Component
class MyCurrencyExchangeProcess {

    public void processMessage(CurrencyExchange currencyExchange) {
        log.info("Do some procesing with CurrencyExchange value {}", currencyExchange);

    }
}

@Slf4j
@Component
class MyCurrencyExchangeTransformer {

    public CurrencyExchange processMessage(CurrencyExchange currencyExchange) {
        currencyExchange.setConversionMultiple(currencyExchange.getConversionMultiple().multiply(BigDecimal.TEN));
        return currencyExchange;
    }
}