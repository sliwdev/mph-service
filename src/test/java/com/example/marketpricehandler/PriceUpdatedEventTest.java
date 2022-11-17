package com.example.marketpricehandler;

import com.example.marketpricehandler.exception.MessageLineParsingException;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.example.marketpricehandler.domain.InstrumentName.EUR_JPY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceUpdatedEventTest {

    @Test
    void shouldParse() {
        //given
        String message = "107,EUR/JPY,119.60,119.90,01-06-2020 12:01:02:002";

        //when
        PriceUpdatedEvent result = PriceUpdatedEvent.from(message);

        //then
        assertEquals(107, result.id());
        assertEquals(EUR_JPY, result.instrumentName());
        assertEquals(119.60, result.bid());
        assertEquals(119.90, result.ask());
        assertEquals(Instant.parse("2020-06-01T12:01:02.002Z"), result.timestamp());
    }

    @Test
    void shouldThrow() {
        //given
        String message = ",,,,,,,,,";

        //expect
        assertThrows(MessageLineParsingException.class, () -> PriceUpdatedEvent.from(message));
    }
}