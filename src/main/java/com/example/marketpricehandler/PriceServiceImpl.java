package com.example.marketpricehandler;

import com.example.marketpricehandler.domain.InstrumentName;
import com.example.marketpricehandler.domain.Price;

import java.util.Optional;

class PriceServiceImpl implements PriceService {

    private final double bidCommissionMultiplier;
    private final double askCommissionMultiplier;

    private final PriceRepository priceRepository;

    PriceServiceImpl(PriceRepository priceRepository,
                     double bidCommissionMultiplier,
                     double askCommissionMultiplier) {
        this.bidCommissionMultiplier = bidCommissionMultiplier;
        this.askCommissionMultiplier = askCommissionMultiplier;
        this.priceRepository = priceRepository;
    }

    @Override
    public Optional<Price> getPrice(InstrumentName instrumentName) {
        return priceRepository.get(instrumentName);
    }

    @Override
    public void updatePrice(PriceUpdatedEvent event) {
        Price price = new Price(event.instrumentName(), event.bid(), event.ask(), event.timestamp());
        price.addCommission(bidCommissionMultiplier, askCommissionMultiplier);
        priceRepository.update(price);
    }

}
