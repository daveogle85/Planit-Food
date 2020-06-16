package com.planitfood.typeConverters;

import com.planitfood.models.Meal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MealListTypeConverterTests {
    @Test
    public void convertsListOfMealsToAListOfIds() throws Exception {
        MealListTypeConverter mealListTypeConverter = new MealListTypeConverter();
        Meal test1 = new Meal("1", "Test_1");
        Meal test2 = new Meal("2", "Test_2");
        Meal test3 = new Meal("3", "Test_3");
        ArrayList<Meal> testList = new ArrayList<>();
        testList.add(test1);
        testList.add(test2);
        testList.add(test3);

        List<String> result = mealListTypeConverter.convert(testList);
        Assertions.assertEquals(result.size(), 3);
        Assertions.assertEquals(result.get(0), "1");
        Assertions.assertEquals(result.get(1), "2");
        Assertions.assertEquals(result.get(2), "3");
    }

    @Test
    public void convertsListOfStringIdsToAListOfMeals() throws Exception {
        MealListTypeConverter mealListTypeConverter = new MealListTypeConverter();
        String test1 = "1";
        String test2 = "2";
        String test3 = "3";

        ArrayList<String> testList = new ArrayList<>();
        testList.add(test1);
        testList.add(test2);
        testList.add(test3);

        List<Meal> result = mealListTypeConverter.unconvert(testList);
        Assertions.assertEquals(result.size(), 3);
        Assertions.assertEquals(result.get(0).getId(), "1");
        Assertions.assertEquals(result.get(1).getId(), "2");
        Assertions.assertEquals(result.get(2).getId(), "3");
    }
}
