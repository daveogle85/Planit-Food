package com.planitfood.typeConverters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import com.planitfood.models.Quantity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuantitiesTypeConverter implements DynamoDBTypeConverter<Map<String, String>, Map<String, Quantity>> {

    public static Dish convertIngredientsToQuantities(Dish dish) {
        List<Ingredient> ingredients = dish.getIngredients();
        HashMap<String, Quantity> quantities = new HashMap<>();

        if (ingredients != null) {
            for (Ingredient ingredient : ingredients) {
                if (ingredient.getQuantity() != null) {
                    Quantity quantity = new Quantity(ingredient.getQuantity(), ingredient.getUnit(), ingredient.getId());
                    quantities.put(ingredient.getId(), quantity);
                }
            }
            dish.setQuantities(quantities);
        }
        return dish;
    }

    public static Dish convertQuantitiesToIngredients(Dish dish) {
        Map<String, Quantity> quantities = dish.getQuantities();

        if(quantities != null) {
            for (Ingredient ingredient : dish.getIngredients()) {
                Quantity quantity = quantities.get(ingredient.getId());

                if (quantity != null) {
                    ingredient.setQuantity(quantity.getQuantity());
                    ingredient.setUnit(quantity.getUnit());
                }
            }
        }
        return dish;
    }

    @Override
    public Map<String, String> convert(Map<String, Quantity> quantities) {
        Gson gson = new Gson();
        Map<String, String> converted = new HashMap<>();

        for (Map.Entry<String, Quantity> entry : quantities.entrySet()) {
           converted.put(entry.getKey(), gson.toJson(entry.getValue()));
        }
        return converted;
    }

    @Override
    public Map<String, Quantity> unconvert(Map<String, String> quantities) {
        Gson gson = new Gson();
        Map<String, Quantity> converted = new HashMap<>();

        for (Map.Entry<String, String> entry : quantities.entrySet()) {
            converted.put(entry.getKey(), gson.fromJson(entry.getValue(), Quantity.class));
        }
        return converted;
    }
}
