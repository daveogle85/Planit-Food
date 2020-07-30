package com.planitfood.typeConverters;

import com.planitfood.enums.Unit;
import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import com.planitfood.models.Quantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QuantitiesTypeConverterTests {
    @Test
    public void convertsIngredientsToQuantities() throws Exception {
        Ingredient i1 = new Ingredient("1", "test 1");
        Ingredient i2 = new Ingredient("2", "test 2");
        Quantity q1 = new Quantity(12.1, Unit.UNIT);
        Quantity q2 = new Quantity(2, Unit.G);
        i1.setQuantity(12.1);
        i2.setQuantity(2);
        i2.setUnit(Unit.G);
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>(Arrays.asList(i1, i2));
        Dish dish = new Dish("1");
        dish.setIngredients(ingredients);

        dish = QuantitiesTypeConverter.convertIngredientsToQuantities(dish);

        Assertions.assertNotNull(dish.getQuantities());
        Assertions.assertEquals(q1, dish.getQuantities().get("1"));
        Assertions.assertEquals(q2, dish.getQuantities().get("2"));
    }

    @Test
    public void convertsQuantitiesToIngredients() throws Exception {
        Quantity q1 = new Quantity(12.1, Unit.UNIT);
        Quantity q2 = new Quantity(2, Unit.G);
        Ingredient i1 = new Ingredient("1", "test 1");
        Ingredient i2 = new Ingredient("2", "test 2");
        Ingredient i3 = new Ingredient("3", "test 3");
        Dish dish = new Dish("1");
        HashMap<String, Quantity> quants = new HashMap<>();
        quants.put("1", q1);
        quants.put("2", q2);
        dish.setQuantities(quants);
        dish.setIngredients(new ArrayList<>(Arrays.asList(i1, i2, i3)));

        dish = QuantitiesTypeConverter.convertQuantitiesToIngredients(dish);

        Assertions.assertEquals(q1.getQuantity(), dish.getIngredients().get(0).getQuantity());
        Assertions.assertEquals(q1.getUnit(), dish.getIngredients().get(0).getUnit());
        Assertions.assertEquals(q2.getQuantity(), dish.getIngredients().get(1).getQuantity());
        Assertions.assertEquals(q2.getUnit(), dish.getIngredients().get(1).getUnit());
        Assertions.assertNull(dish.getIngredients().get(2).getQuantity());
    }

    @Test
    public void convertQuantitiesToString() throws Exception {
        QuantitiesTypeConverter qtc = new QuantitiesTypeConverter();
        HashMap<String, Quantity> test = new HashMap<>();
        test.put("1", new Quantity(10, Unit.CUP));
        Map result = qtc.convert(test);
        Assertions.assertEquals("{\"quantity\":10.0,\"unit\":\"CUP\"}", result.get("1"));
    }

    @Test
    public void convertStringToQuantites() throws Exception {
        QuantitiesTypeConverter qtc = new QuantitiesTypeConverter();
        HashMap<String, String> test = new HashMap<>();
        test.put("1", "{\"quantity\":10.0,\"unit\":\"CUP\"}");
        Quantity expected = new Quantity(10, Unit.CUP);
        Map result = qtc.unconvert(test);
        Assertions.assertEquals(expected, result.get("1"));
    }
}
