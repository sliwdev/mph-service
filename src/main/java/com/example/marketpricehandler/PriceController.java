package com.example.marketpricehandler;

import com.example.marketpricehandler.domain.InstrumentName;
import com.example.marketpricehandler.domain.Price;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/prices")
class PriceController {

    private final PriceService priceService;

    PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @Get("/{instrumentName}")
    HttpResponse<Price> getPrice(InstrumentName instrumentName) {
        return priceService.getPrice(instrumentName)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }
}
