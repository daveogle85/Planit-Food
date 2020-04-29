package com.planitfood.queries;

import com.planitfood.data.StaticData;
import com.planitfood.mealdetails.PlanitFoodDataFetcher;
import com.planitfood.models.Meal;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MealDataFetcher implements IDataFetcher {

    public String getArgument(DataFetchingEnvironment dataFetchingEnvironment) {
        return dataFetchingEnvironment.getArgument("id");
    }

    public List<Meal> getAllValues() {
        return StaticData.getMeals();
    }

    public Meal findValueFromArg(String arg) {
        return StaticData.getMeals().stream().filter(d -> d.getId().equals(arg)).findFirst().orElse(null);
    }

    public DataFetcher getMeals() {
        return dataFetchingEnvironment -> PlanitFoodDataFetcher.valueFetcher(this, dataFetchingEnvironment);
    }

    public DataFetcher getDishesForMeal() {
        return dataFetchingEnvironment -> {
            Meal meal = dataFetchingEnvironment.getSource();
            return meal.getSides();
        };
    }
}
