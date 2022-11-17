package com.example.marketpricehandler;

import com.example.marketpricehandler.domain.InstrumentName;
import com.example.marketpricehandler.exception.UnknownInstrumentTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InstrumentNameTest {

    @Test
    public void shouldReturnValue() {
        //given
        String stringValue = "EUR_JPY";

        //when
        InstrumentName result = InstrumentName.forValue(stringValue);

        //then
        assertEquals(result, InstrumentName.EUR_JPY);
    }

    @Test
    public void shouldThrowException() {
        //given
        String unsupported = "unsupported";
        Executable executable = () -> InstrumentName.forValue(unsupported);

        //expect
        assertThrows(UnknownInstrumentTypeException.class, executable);
    }
}