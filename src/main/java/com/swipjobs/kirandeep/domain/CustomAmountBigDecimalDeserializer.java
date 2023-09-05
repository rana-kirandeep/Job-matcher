package com.swipjobs.kirandeep.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class CustomAmountBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String stringValue = jsonParser.getValueAsString();
        String numericValue = stringValue.replaceAll("[^\\d.]", "");
        return new BigDecimal(numericValue);
    }
}

