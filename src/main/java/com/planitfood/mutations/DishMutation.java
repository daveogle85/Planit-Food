package com.planitfood.mutations;

import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import graphql.schema.DataFetcher;

import java.util.ArrayList;

public class DishMutation {
    public DataFetcher addDish() {
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            String id = name + "_id"; // Will be a UUID or ID from the database
            ArrayList<String> ingredients = dataFetchingEnvironment.getArgument("ingredients");
            String notes = dataFetchingEnvironment.getArgument("notes");
            Float cookingTime = null;

            Double cookingTimeDouble = dataFetchingEnvironment.getArgument("cookingTime");
            if (cookingTimeDouble != null) {
                cookingTime = Float.parseFloat(cookingTimeDouble.toString());
            }

            Dish dish = new Dish(id, name);
            if (ingredients != null) dish = setIngredients(dish, ingredients);
            if (notes != null) dish.setNotes(notes);
            if (cookingTime != null) dish.setCookingTime(cookingTime);
            return addDishToDatabase(dish);
        };
    }

    public Dish setIngredients(Dish dish, ArrayList<String> ingredientNames) {
        for (int i = 0; i < ingredientNames.size(); i++) {
            dish.addIngredient(new Ingredient(ingredientNames.get(i)));
        }
        return dish;
    }

    private Dish addDishToDatabase(Dish dish) {
        System.out.println("Added " + dish.toString() + " to database");
        return dish;
    }
}
