package com.planitfood.typeConverters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class LocalDateTypeConverterTests {
    @Test
    public void convertsLocalDateToString() throws Exception {
        LocalDateTypeConverter localDateTypeConverter = new LocalDateTypeConverter();
        LocalDate localDate = LocalDate.of(2020, 12, 31);
        String result = localDateTypeConverter.convert(localDate);

        Assertions.assertEquals(result, "2020-12-31");
    }

    @Test
    public void convertsStringToLocalDate() throws Exception {
        LocalDateTypeConverter localDateTypeConverter = new LocalDateTypeConverter();
        String toConvert = "2020-12-31";
        LocalDate result = localDateTypeConverter.unconvert(toConvert);
        Assertions.assertEquals(result.getYear(), 2020);
        Assertions.assertEquals(result.getMonth().getValue(), 12);
        Assertions.assertEquals(result.getDayOfMonth(), 31);
    }
}
