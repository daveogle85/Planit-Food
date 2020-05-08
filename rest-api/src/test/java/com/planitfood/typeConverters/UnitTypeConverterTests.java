package com.planitfood.typeConverters;

import com.planitfood.enums.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnitTypeConverterTests {
    @Test
    public void convertsUnitToString() throws Exception {
        UnitTypeConverter unitTypeConverter = new UnitTypeConverter();
        Unit kg = Unit.KG;
        String result = unitTypeConverter.convert(kg);
        Assertions.assertEquals(result, "KG");
    }

    @Test
    public void convertsStringToUnit() throws Exception {
        UnitTypeConverter unitTypeConverter = new UnitTypeConverter();
        String kg = "KG";
        Unit result = unitTypeConverter.unconvert(kg);
        Assertions.assertEquals(result, Unit.KG);
    }
}
