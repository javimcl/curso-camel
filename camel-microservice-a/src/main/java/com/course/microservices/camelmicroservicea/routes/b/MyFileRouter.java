package com.course.microservices.camelmicroservicea.routes.b;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
public class MyFileRouter extends RouteBuilder {

    @Autowired
    DeciderBean deciderBean;

    @Override
    public void configure() throws Exception {

        from("file:files/input")
                .routeId("Files-Input-Route")
                .transform().body(String.class)
                .choice()
                .when(simple("${file:ext} ends with 'xml'"))

                .log("XML File")
                //.when(simple("${body} contains 'USD'"))
                .when(method(deciderBean))
                .log("Not an XML FILE BUT contains USD")
                .otherwise()
                .log("not an XML File")
                .end()
                .to("direct://log-file-values")
                .to("file:files/output");

        from("direct:log-file-values")
                .log("${messageHistory} ${headers:CamelFileAbsolute}")
                .log("${file:name}")
                .log("${file:size} ${file:modified}")
                .log("${routeId} ${camelId} ${body}");
    }
}

@Slf4j
@Component
class DeciderBean {
    public boolean isThisCondional(@Body String body, @Headers Map<String, String> headers, @ExchangeProperties Map<String, String> exchangeProperties) {
        log.info("{} {} {}", body, headers, exchangeProperties);
        return true;
    }
}
