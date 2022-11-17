package com.example.marketpricehandler.exception;

public class MessageLineParsingException extends RuntimeException {
    public MessageLineParsingException(String message, String exceptionMessage) {
        super("Could not parse malformed message " + message + "\n" + exceptionMessage);
    }
}
