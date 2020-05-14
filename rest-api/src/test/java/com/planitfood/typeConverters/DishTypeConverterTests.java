package com.planitfood.typeConverters;

import com.planitfood.models.Dish;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DishTypeConverterTests {
    @Test
    public void convertsListOfDishesToAListOfIds() throws Exception {
        DishTypeConverter dishTypeConverter = new DishTypeConverter();
        Dish test1 = new Dish("1", "Test_1");
        Dish test2 = new Dish("2", "Test_2");
        Dish test3 = new Dish("3", "Test_3");
        ArrayList<Dish> testList = new ArrayList<>();
        testList.add(test1);
        testList.add(test2);
        testList.add(test3);

        List<String> result = dishTypeConverter.convert(testList);
        Assertions.assertEquals(result.size(), 3);
        Assertions.assertEquals(result.get(0), "1");
        Assertions.assertEquals(result.get(1), "2");
        Assertions.assertEquals(result.get(2), "3");
    }

    @Test
    public void convertsListOfStringIdsToAListOfDishes() throws Exception {
        DishTypeConverter dishTypeConverter = new DishTypeConverter();
        String test1 = "1";
        String test2 = "2";
        String test3 = "3";

        ArrayList<String> testList = new ArrayList<>();
        testList.add(test1);
        testList.add(test2);
        testList.add(test3);

        List<Dish> result = dishTypeConverter.unconvert(testList);
        Assertions.assertEquals(result.size(), 3);
        Assertions.assertEquals(result.get(0).getId(), "1");
        Assertions.assertEquals(result.get(1).getId(), "2");
        Assertions.assertEquals(result.get(2).getId(), "3");
    }
}
