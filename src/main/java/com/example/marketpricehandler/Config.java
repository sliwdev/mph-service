package com.example.marketpricehandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Factory
class Config {

    @Singleton
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        //todo configure reasonable Instant serialization
        return objectMapper;
    }

    @Singleton
    PriceRepository priceRepository() {
        return new PriceRepository();
    }

    @Singleton
    PriceService priceService(PriceRepository priceRepository,
                              @Value("${commission.multiplier.bid}") double bidCommissionMultiplier,
                              @Value("${commission.multiplier.ask}") double askCommissionMultiplier) {
        return new PriceServiceImpl(priceRepository, bidCommissionMultiplier, askCommissionMultiplier);
    }

    @Singleton
    PriceUpdateHandler priceUpdateHandler(PriceService priceService) {
        return new PriceUpdateHandler(priceService);
    }
}
