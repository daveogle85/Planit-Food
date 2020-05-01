package com.planitfood.mealdetails;

import com.planitfood.queries.*;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlanitFoodDataFetcher {

    private DayDataFetcher dayDataFetcher = new DayDataFetcher();
    private MealDataFetcher mealDataFetcher = new MealDataFetcher();
    private DishDataFetcher dishDataFetcher = new DishDataFetcher();
    private IngredientDataFetcher ingredientDataFetcher = new IngredientDataFetcher();

    public DataFetcher getDaysForDateRange() {
        return dayDataFetcher.getDaysForDateRange();
    }

    public DataFetcher getMeals() {
        return mealDataFetcher.getMeals();
    }

    public DataFetcher getDishesForMeal() {
        return mealDataFetcher.getDishesForMeal();
    }

    public DataFetcher getDishes() {
        return dishDataFetcher.getDishes();
    }

    public DataFetcher getIngredients() {
        return ingredientDataFetcher.getIngredients();
    }

    public DataFetcher getIngredientsForDish() {
        return ingredientDataFetcher.getIngredientsForDish();
    }

    /**
     * Returns back either all the values (e.g. all dishes),
     * a specified value from a given argument (e.g. dish by id),
     * or null if nothing found. All values returned in a list.
     *
     * @param dataFetcher
     * @param dataFetchingEnvironment
     * @param <T>
     * @return
     */
    public static <T> List<T> valueFetcher(IDataFetcher dataFetcher, DataFetchingEnvironment dataFetchingEnvironment) {
        {
            String arg = dataFetcher.getArgument(dataFetchingEnvironment);
            if (arg == null) {
                return dataFetcher.getAllValues();
            }

            T value = dataFetcher.findValueFromArg(arg);
            if (value == null) {
                return null;
            }
            ArrayList<T> foundValue = new ArrayList();
            foundValue.add(value);
            return foundValue;
        }
    }
}
