package com.example.marketpricehandler;

import com.example.marketpricehandler.domain.InstrumentName;
import com.example.marketpricehandler.domain.Price;
import com.example.marketpricehandler.exception.InvalidPriceTimestampException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class PriceRepository {

    Map<InstrumentName, Price> prices = new ConcurrentHashMap<>();

    public void update(Price price) {
        prices.merge(price.getInstrumentName(), price, (currentPrice, newPrice) -> {
            validateTimestamp(currentPrice, newPrice);
            return newPrice;
        });
    }

    private static void validateTimestamp(Price currentPrice, Price newPrice) {
        if (newPrice.isBefore(currentPrice)) {
            throw new InvalidPriceTimestampException("New price cannot be older than current, current price: " + currentPrice + ", new price: " + newPrice);
        }
    }

    public Optional<Price> get(InstrumentName instrumentName) {
        return Optional.ofNullable(prices.get(instrumentName));
    }
}
