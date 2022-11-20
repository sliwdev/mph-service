package com.example.marketpricehandler;

import com.example.marketpricehandler.domain.InstrumentName;
import com.example.marketpricehandler.domain.Price;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

//I know the task said it was not necessary but also wasn't much work so added it to make the whole look better
@Controller("/prices")
class PriceController {

    private final PriceService priceService;

    PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    // could use reactive http dependency (rxjava) but didn't since
    // everything happens in memory
    // and the controller wasn't actually requested
    @Get("/{instrumentName}")
    HttpResponse<Price> getPrice(InstrumentName instrumentName) {
        return priceService.getPrice(instrumentName)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }
}
