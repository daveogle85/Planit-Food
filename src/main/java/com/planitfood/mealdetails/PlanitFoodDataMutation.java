package com.planitfood.mealdetails;

import com.planitfood.mutations.DayMutation;
import com.planitfood.mutations.DishMutation;
import com.planitfood.mutations.IngredientMutation;
import com.planitfood.mutations.MealMutation;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

@Component
public class PlanitFoodDataMutation {

    private DayMutation dayMutation = new DayMutation();
    private MealMutation mealMutation = new MealMutation();
    private DishMutation dishMutation = new DishMutation();
    private IngredientMutation ingredientMutation = new IngredientMutation();

    public DataFetcher addDay() {
        return dayMutation.addDay();
    }

    public DataFetcher addMeal() {
        return mealMutation.addMeal();
    }

    public DataFetcher addDish() {
        return dishMutation.addDish();
    }

    public DataFetcher addIngredient() {
        return ingredientMutation.addIngredient();
    }
}
