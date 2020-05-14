package com.planitfood.typeConverters;

import com.planitfood.enums.DishType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DishTypeTypeConverterTests {
    @Test
    public void convertsDishTypeToString() throws Exception {
        DishTypeTypeConverter dishTypeTypeConverter = new DishTypeTypeConverter();
        DishType side = DishType.SIDE;
        String result = dishTypeTypeConverter.convert(side);
        Assertions.assertEquals(result, "SIDE");
    }

    @Test
    public void convertsStringToDishType() throws Exception {
        DishTypeTypeConverter dishTypeTypeConverter = new DishTypeTypeConverter();
        String side = "SIDE";
        DishType result = dishTypeTypeConverter.unconvert(side);
        Assertions.assertEquals(result, DishType.SIDE);
    }
}
