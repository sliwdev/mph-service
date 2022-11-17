package com.example.marketpricehandler.domain;

import com.example.marketpricehandler.exception.InvalidPriceException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

public class Price {

    //serves as an id
    private InstrumentName instrumentName;
    private BigDecimal bid;
    private BigDecimal ask;
    private Instant timestamp;

    public Price(InstrumentName instrumentName, double bid, double ask, Instant timestamp) {
        if (bid > ask) {
            throw new InvalidPriceException(bid, ask);
        }
        this.instrumentName = instrumentName;
        this.bid = new BigDecimal(bid).setScale(4, RoundingMode.HALF_UP);
        this.ask = new BigDecimal(ask).setScale(4, RoundingMode.HALF_UP);
        this.timestamp = timestamp;
    }

    public void addCommission(double bidCommissionMultiplier, double askCommissionMultiplier) {
        bid = bid.multiply(new BigDecimal(bidCommissionMultiplier)).setScale(4, RoundingMode.HALF_UP);
        ask = ask.multiply(new BigDecimal(askCommissionMultiplier)).setScale(4, RoundingMode.HALF_UP);
    }

    public boolean isBefore(Price price) {
        return this.timestamp.isBefore(price.timestamp);
    }

    public InstrumentName getInstrumentName() {
        return instrumentName;
    }

    public double getBid() {
        return bid.doubleValue();
    }

    public double getAsk() {
        return ask.doubleValue();
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
