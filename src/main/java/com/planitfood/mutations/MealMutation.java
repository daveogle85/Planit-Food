package com.planitfood.mutations;

import com.planitfood.models.Dish;
import com.planitfood.models.Meal;
import graphql.schema.DataFetcher;

import java.util.ArrayList;

public class MealMutation {
    public DataFetcher addMeal() {
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            String id = "meal_id_" + name;
            ArrayList<String> dishIds = dataFetchingEnvironment.getArgument("dishes");
            String notes = dataFetchingEnvironment.getArgument("notes");

            Meal meal = new Meal(id, name);
            if (dishIds != null) {
                for (String dishId : dishIds) {
                    Dish dish = new Dish(dishId, "name_of_" + id);
                    meal.addDish(dish);
                }
            }
            if (notes != null) meal.setNotes(notes);
            return addMealToDatabase(meal);
        };
    }

    private Meal addMealToDatabase(Meal meal) {
        System.out.println("Added " + meal.toString() + " to database");
        return meal;
    }
}
