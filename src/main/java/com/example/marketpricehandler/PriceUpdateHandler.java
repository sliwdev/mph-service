package com.example.marketpricehandler;

import com.example.marketpricehandler.exception.InvalidPriceException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

class PriceUpdateHandler {

    private final PriceService priceService;

    PriceUpdateHandler(PriceService priceService) {
        this.priceService = priceService;
    }

    void onMessage(String message) throws IOException {
        //assuming input could be large
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(message.getBytes(UTF_8));
             Scanner scanner = new Scanner(inputStream, UTF_8)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                PriceUpdatedEvent price = PriceUpdatedEvent.from(line);
                if (price.bid() >= price.ask()) {
                    throw new InvalidPriceException(price.bid(), price.ask());
                }
                priceService.updatePrice(price);
            }
            if (scanner.ioException() != null) {
                throw scanner.ioException();
            }
        }
    }

}
