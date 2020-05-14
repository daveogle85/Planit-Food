package com.planitfood.typeConverters;

import com.planitfood.models.Ingredient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class IngredientsTypeConverterTests {
    @Test
    public void convertsListOfIngredientsToAListOfIds() throws Exception {
        IngredientsTypeConverter ingredientsTypeConverter = new IngredientsTypeConverter();
        Ingredient test1 = new Ingredient("1", "Test_1");
        Ingredient test2 = new Ingredient("2", "Test_2");
        Ingredient test3 = new Ingredient("3", "Test_3");
        ArrayList<Ingredient> testList = new ArrayList<>();
        testList.add(test1);
        testList.add(test2);
        testList.add(test3);

        List<String> result = ingredientsTypeConverter.convert(testList);
        Assertions.assertEquals(result.size(), 3);
        Assertions.assertEquals(result.get(0), "1");
        Assertions.assertEquals(result.get(1), "2");
        Assertions.assertEquals(result.get(2), "3");
    }

    @Test
    public void convertsListOfStringIdsToAListOfIngredients() throws Exception {
        IngredientsTypeConverter ingredientsTypeConverter = new IngredientsTypeConverter();
        String test1 = "1";
        String test2 = "2";
        String test3 = "3";

        ArrayList<String> testList = new ArrayList<>();
        testList.add(test1);
        testList.add(test2);
        testList.add(test3);

        List<Ingredient> result = ingredientsTypeConverter.unconvert(testList);
        Assertions.assertEquals(result.size(), 3);
        Assertions.assertEquals(result.get(0).getId(), "1");
        Assertions.assertEquals(result.get(1).getId(), "2");
        Assertions.assertEquals(result.get(2).getId(), "3");
    }
}
