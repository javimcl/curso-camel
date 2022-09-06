package com.course.microservices.camelmicroserviceb.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//@Component
public class KafkaReceiverRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("kafka:myKafkaTopic")
        .unmarshal()
        .jacksonXml(CurrencyExchange.class)
        .to("log:received-message-from-active-mq");

    }

}