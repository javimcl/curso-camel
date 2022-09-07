package com.course.microservices.camelmicroservicea.routes.patterns;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class DynamicRouterBean {

    int invocations;

    public String decideNextEndPoint(@ExchangeProperties Map<String, String> properties, @Headers Map<String, String> headers, @Body String body) {
        log.info("{} {} {}", properties, headers, body);
        invocations++;
        if (invocations % 3 == 0) {
            return "direct:endpoint1";
        }
        if (invocations % 3 == 1) {
            return "direct:endpoint2,direct:endpoint3";
        }
        return null;
    }
}
