package com.planitfood.mealdetails;

import com.planitfood.mutations.IngredientMutation;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

@Component
public class PlanitFoodDataMutation {

    private IngredientMutation ingredientMutation = new IngredientMutation();

    public DataFetcher addIngredient() {
        return ingredientMutation.addIngredient();
    }
}
