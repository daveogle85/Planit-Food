package com.planitfood.mutations;

import com.planitfood.enums.Unit;
import com.planitfood.models.Ingredient;
import graphql.schema.DataFetcher;

public class IngredientMutation {

    public DataFetcher addIngredient() {
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            Float quantity = null;
            Unit unit = Unit.valueOf(dataFetchingEnvironment.getArgument("unit"));

            Double quantityDouble = dataFetchingEnvironment.getArgument("quantity");
            if (quantityDouble != null) {
                quantity = Float.parseFloat(quantityDouble.toString());
            }

            Ingredient ingredient = new Ingredient(name);
            if (quantity != null) ingredient.setQuantity(quantity);
            if (unit != Unit.UNIT) ingredient.setUnit(unit);
            return addIngredientToDatabase(ingredient);
        };
    }

    private Ingredient addIngredientToDatabase(Ingredient ingredient) {
        System.out.println("Added " + ingredient.getName() + " to database");
        return ingredient;
    }
}
