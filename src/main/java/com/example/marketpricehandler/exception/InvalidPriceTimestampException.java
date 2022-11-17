package com.example.marketpricehandler.exception;

public class InvalidPriceTimestampException extends RuntimeException {
    public InvalidPriceTimestampException(String message) {
        super(message);
    }
}
