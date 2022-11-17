package com.example.marketpricehandler;

import com.example.marketpricehandler.domain.InstrumentName;
import com.example.marketpricehandler.domain.Price;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.example.marketpricehandler.domain.InstrumentName.EUR_JPY;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class PriceControllerTest {

    @Inject
    PriceService priceService;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    PriceController priceController;

    @Test
    public void shouldReturnSomePrice() throws JsonProcessingException {
        //given
        PriceUpdatedEvent event = new PriceUpdatedEvent(123L, EUR_JPY, 119.60, 119.90, Instant.ofEpochMilli(1668702826L));
        priceService.updatePrice(event);

        //when
        HttpResponse<Price> result = priceController.getPrice(EUR_JPY);

        //then
        assertEquals(HttpStatus.OK, result.getStatus());
        Price actual = result.getBody(Price.class).get();
        assertEquals("""
                {"instrumentName":"EUR_JPY","bid":119.4804,"ask":120.0199,"timestamp":1668702.826000000}""",
                objectMapper.writeValueAsString(actual));
    }

}