package com.example.marketpricehandler.domain;

import com.example.marketpricehandler.exception.InvalidPriceException;
import org.junit.jupiter.api.Test;

import static com.example.marketpricehandler.domain.InstrumentName.EUR_JPY;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.MILLIS;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    public void shouldAddCommission() {
        //given
        Price price = new Price(EUR_JPY, 1.2499, 1.2561, now());

        //when
        price.addCommission(0.999, 1.001);

        //then
        assertEquals(1.2487, price.getBid());
        assertEquals(1.2574, price.getAsk());
    }

    @Test
    public void shouldThrowInvalidPrice() {
        // given
        double bidHigherThanAsk = 1.2561;
        double askLowerThanBid = 1.2499;

        //expect
        assertThrows(InvalidPriceException.class, () -> new Price(EUR_JPY, bidHigherThanAsk, askLowerThanBid, now()));
    }

    @Test
    public void shouldIndicateIfIsBefore() {
        //given
        Price earlier = new Price(EUR_JPY, 1.2499, 1.2561, now());
        Price later = new Price(EUR_JPY, 1.2499, 1.2561, now().plus(2, MILLIS));

        //when
        boolean result = earlier.isBefore(later);

        //then
        assertTrue(result);
    }

}