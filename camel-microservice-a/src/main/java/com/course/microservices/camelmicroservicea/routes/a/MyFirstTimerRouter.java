/**
 * 
 */
package com.course.microservices.camelmicroservicea.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author JAVIM
 *
 */
//@Component
public class MyFirstTimerRouter extends RouteBuilder {



	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;
	
	@Autowired 
	private SimpleLoggingProcessingComponente loggingProcessingComponente;
	
	@Override
	public void configure() throws Exception {
		// queue
		// transformation
		// log
		// Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null} 
		from("timer:first-timer") //queue
		.log("${body}")
		.transform().constant("My constant Message")
		.log("${body}")
		//.transform().constant("Time now is" + LocalDateTime.now())
		.bean(getCurrentTimeBean)
		.log("${body}")//Time now is2022-08-31T12:50:36.500438500
		.bean(loggingProcessingComponente)
		.log("${body}")
		.process(new SimpleLoggingProcessor())
		.to("log:firt-timer"); //database
	}

}

@Component
class GetCurrentTimeBean {
	public String getCurrentTime() {
		return "Time now is" + LocalDateTime.now();
	}
}

@Component
class SimpleLoggingProcessingComponente {
	
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponente.class);
	public void process(String message) {
		logger.info("SimpleLoggingProcessingComponente {}", message);
	}
}

class SimpleLoggingProcessor implements Processor {
	
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());

	}

}
