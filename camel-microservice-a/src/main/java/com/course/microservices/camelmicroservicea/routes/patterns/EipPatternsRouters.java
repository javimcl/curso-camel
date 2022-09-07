package com.course.microservices.camelmicroservicea.routes.patterns;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.camel.AggregationStrategy;

@Component
public class EipPatternsRouters extends RouteBuilder {

    @Autowired
    DynamicRouterBean dynamicRouterBean;

    @Override
    public void configure() throws Exception {
        //pipelines
        // Content Based Routing - choice()
        // Multicast

//        from("timer:multicast?period=10000")
//                .multicast()
//                .to("log:something1", "log:something2", "log:something3");

//        from("file:files/cvs")
//                .convertBodyTo(String.class)
//                .split(body())
//                .to("activemq:split-queue");

        from("file:files/aggregation-json")
                .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
                .aggregate(simple("${body.to}"), new ArrayListAggregationStrategy())
                .completionSize(3)
                .completionTimeout(HIGHEST)
                .to("log:aggregate-json");
        String routingList = "direct:endpoint1,direct:endpoint3";

//        from("timer:routingList?period=10000")
//                .transform().constant("My Message")
//                .routingSlip(simple(routingList));


        from("timer:dynamicRouting?period=10000")
                .transform().constant("My Message")
                .dynamicRouter(method(dynamicRouterBean));

        from("direct:endpoint1")
                .to("log:direct:endpoint1");
        from("direct:endpoint2")
                .to("log:direct:endpoint2");
        from("direct:endpoint3")
                .to("log:direct:endpoint3");
    }
}
