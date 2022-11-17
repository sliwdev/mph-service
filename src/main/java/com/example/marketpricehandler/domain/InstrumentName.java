package com.example.marketpricehandler.domain;

import com.example.marketpricehandler.exception.UnknownInstrumentTypeException;

public enum InstrumentName {

    EUR_USD,
    GBP_USD,
    EUR_JPY;

    public static InstrumentName forValue(String stringValue) {
        for (InstrumentName value : InstrumentName.values()) {
            if (value.name().equals(stringValue)) {
                return value;
            }
        }
        throw new UnknownInstrumentTypeException(stringValue);
    }
}
