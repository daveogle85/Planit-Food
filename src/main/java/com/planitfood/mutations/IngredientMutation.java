package com.planitfood.mutations;

import com.planitfood.mealdetails.Unit;
import com.planitfood.models.Ingredient;
import graphql.schema.DataFetcher;

public class IngredientMutation {

    public DataFetcher addIngredient() {
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            Float quantity = Float.parseFloat(dataFetchingEnvironment.getArgument("quantity").toString());
            Unit unit = Unit.valueOf(dataFetchingEnvironment.getArgument("unit"));

            if (quantity != null && unit != null) {
                return addIngredient(name, quantity, unit);
            } else if (quantity != null) {
                return addIngredient(name, quantity);
            } else {
                return addIngredient(name);
            }
        };
    }

    public Ingredient addIngredient(String name) {
        Ingredient ingredient = new Ingredient(name);
        return addIngredientToDatabase(ingredient);
    }

    public Ingredient addIngredient(String name, Float quantity) {
        Ingredient ingredient = new Ingredient(name);
        ingredient.setQuantity(quantity);
        return addIngredientToDatabase(ingredient);
    }

    public Ingredient addIngredient(String name, Float quantity, Unit unit) {
        Ingredient ingredient = new Ingredient(name);
        ingredient.setQuantity(quantity);
        ingredient.setUnit(unit);
        return addIngredientToDatabase(ingredient);
    }

    private Ingredient addIngredientToDatabase(Ingredient ingredient) {
        System.out.println("Added " + ingredient.getName() + " to database");
        return ingredient;
    }
}
