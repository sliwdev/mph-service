package com.example.marketpricehandler.exception;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException(Double bid, Double ask) {
        super("Bid higher or equal to ask, bid: " + bid + ", ask: " + ask);
    }
}
