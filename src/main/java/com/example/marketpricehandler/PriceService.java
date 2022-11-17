package com.example.marketpricehandler;

import com.example.marketpricehandler.domain.InstrumentName;
import com.example.marketpricehandler.domain.Price;

import java.util.Optional;

interface PriceService {
    Optional<Price> getPrice(InstrumentName instrumentName);

    void updatePrice(PriceUpdatedEvent event);
}
