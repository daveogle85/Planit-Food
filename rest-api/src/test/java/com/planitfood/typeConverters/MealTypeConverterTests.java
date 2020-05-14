package com.planitfood.typeConverters;

import com.planitfood.models.Meal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MealTypeConverterTests {
    @Test
    public void convertsMealsToAnIds() throws Exception {
        MealTypeConverter mealTypeConverter = new MealTypeConverter();
        Meal test = new Meal("1", "Test_1");
        String result = mealTypeConverter.convert(test);
        Assertions.assertEquals(result, "1");
    }

    @Test
    public void convertsAStringIdToADish() throws Exception {
        MealTypeConverter mealTypeConverter = new MealTypeConverter();
        String test = "1";
        Meal result = mealTypeConverter.unconvert(test);
        Assertions.assertEquals(result.getId(), "1");
    }
}
