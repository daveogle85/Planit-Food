package com.planitfood.mealdetails;

import com.planitfood.mutations.DishMutation;
import com.planitfood.mutations.IngredientMutation;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

@Component
public class PlanitFoodDataMutation {

    private IngredientMutation ingredientMutation = new IngredientMutation();
    private DishMutation dishMutation = new DishMutation();

    public DataFetcher addIngredient() {
        return ingredientMutation.addIngredient();
    }

    public DataFetcher addDish() {
        return dishMutation.addDish();
    }
}
