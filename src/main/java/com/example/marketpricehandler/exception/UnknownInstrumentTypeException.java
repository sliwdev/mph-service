package com.example.marketpricehandler.exception;

public class UnknownInstrumentTypeException extends RuntimeException {
    public UnknownInstrumentTypeException(String message) {
        super("Unknown instrument type: " + message);
    }
}
