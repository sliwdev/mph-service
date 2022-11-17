package com.example.marketpricehandler;

import com.example.marketpricehandler.domain.InstrumentName;
import com.example.marketpricehandler.domain.Price;
import com.example.marketpricehandler.exception.InvalidPriceException;
import com.example.marketpricehandler.exception.MessageLineParsingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceUpdateHandlerTest {

    private int updatesCounter = 0;

    // I'd normally use Mockito but not for a single usage
    private PriceService mockPriceService = new PriceService() {

        @Override
        public Optional<Price> getPrice(InstrumentName instrumentName) {
            return Optional.empty();
        }

        @Override
        public void updatePrice(PriceUpdatedEvent event) {
            updatesCounter += 1;
        }
    };

    PriceUpdateHandler handler = new PriceUpdateHandler(mockPriceService);

    @Test
    public void shouldParseMessage() throws IOException {
        //given
        String message = """
                106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001
                107,EUR/JPY,119.60,119.90,01-06-2020 12:01:02:002
                108,GBP/USD,1.2500,1.2560,01-06-2020 12:01:02:002
                109,GBP/USD,1.2499,1.2561,01-06-2020 12:01:02:100
                110,EUR/JPY,119.61,119.91,01-06-2020 12:01:02:110
                """;

        //when
        handler.onMessage(message);

        //then
        assertEquals(5, updatesCounter);
    }

    @Test
    public void shouldThrowForEmptyLine() {
        //given
        String message = """
                106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001
                                
                107,EUR/JPY,119.60,119.90,01-06-2020 12:01:02:002""";

        //expect
        assertThrows(MessageLineParsingException.class, () -> handler.onMessage(message));
    }

    @Test
    public void shouldThrowForInvalidPrice() {
        //given
        String message = "106,EUR/USD,1.2000,1.1000,01-06-2020 12:01:01:001";

        //expect
        assertThrows(InvalidPriceException.class, () -> handler.onMessage(message));
    }

    @Test
    public void shouldThrowForMalformedLine() {
        //given
        String message = "whatever";

        //expect
        assertThrows(MessageLineParsingException.class, () -> handler.onMessage(message));
    }

}