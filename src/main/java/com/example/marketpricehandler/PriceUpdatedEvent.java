package com.example.marketpricehandler;

import com.example.marketpricehandler.domain.InstrumentName;
import com.example.marketpricehandler.exception.MessageLineParsingException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static java.lang.Long.parseLong;
import static java.time.format.DateTimeFormatter.ofPattern;

// since we only have single type of event in our scenario, I did not extract DomainEvent interface
// and separate handlers for separate events as that would be an overkill
record PriceUpdatedEvent(Long id, InstrumentName instrumentName, Double bid, Double ask, Instant timestamp) {

    // format interpreted arbitrarily, some standard format should be established and confirmed with messages source
    public static final DateTimeFormatter DATE_TIME_FORMATTER = ofPattern("d-M-uuuu HH:mm:ss:SSS");
    public static final ZoneId ZONE_ID = ZoneId.ofOffset("", ZoneOffset.UTC);

    public static PriceUpdatedEvent from(String message) {
        // no validation on id since there was no requirement to store incoming messages
        // so hard to indicate if wasn't processed already but in real life would be useful
        long id;
        InstrumentName instrumentName;
        double bid;
        double ask;
        Instant timestamp;

        try {
            String[] parts = message.split(",", 5);
            id = parseLong(parts[0]);
            instrumentName = InstrumentName.valueOf(parts[1].replace("/", "_"));
            bid = Double.parseDouble(parts[2]);
            ask = Double.parseDouble(parts[3]);
            timestamp = LocalDateTime.parse(parts[4], DATE_TIME_FORMATTER)
                    .atZone(ZONE_ID).toInstant();
        } catch (Exception e) {
            throw new MessageLineParsingException(message, e.getMessage());
        }

        return new PriceUpdatedEvent(id, instrumentName, bid, ask, timestamp);
    }
}
