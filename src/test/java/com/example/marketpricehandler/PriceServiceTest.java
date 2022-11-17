package com.example.marketpricehandler;

import com.example.marketpricehandler.domain.Price;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.example.marketpricehandler.domain.InstrumentName.EUR_JPY;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceServiceTest {

    private PriceRepository priceRepository = new PriceRepository();
    private PriceService priceService = new PriceServiceImpl(priceRepository, 0.999, 1.001);

    @Test
    public void shouldGetPrice() {
        //given
        Price expected = new Price(EUR_JPY, 119.60, 119.90, now());
        priceRepository.update(expected);

        //when
        Optional<Price> result = priceService.getPrice(EUR_JPY);

        //then
        assertEquals(expected, result.get());
    }

    @Test
    public void shouldReturnEmpty() {
        //expect
        assertEquals(empty(), priceService.getPrice(EUR_JPY));
    }

    @Test
    public void shouldUpdatePrice() {
        //given
        PriceUpdatedEvent first = new PriceUpdatedEvent(123L, EUR_JPY, 119.60, 119.90, now());
        PriceUpdatedEvent second = new PriceUpdatedEvent(123L, EUR_JPY, 120.00, 121.00, now().plus(2, MILLIS));
        priceService.updatePrice(first);

        //when
        priceService.updatePrice(second);

        //then
        Price result = priceService.getPrice(EUR_JPY).get();
        assertEquals(119.88, result.getBid());
        assertEquals(121.121, result.getAsk());
        assertEquals(second.timestamp(), result.getTimestamp());
    }

}