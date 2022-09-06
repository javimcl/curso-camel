package com.course.microservices.camelmicroserviceb.routes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyEnchangeController {

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange findConversionValue(@PathVariable String from, @PathVariable String to) {
        return new CurrencyExchange(1001L,from, to, BigDecimal.TEN);
    }
}
