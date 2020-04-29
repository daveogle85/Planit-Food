package com.planitfood.mealdetails;

import com.planitfood.data.StaticData;
import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredientDataFetcher implements IDataFetcher {
    public String getArgument(DataFetchingEnvironment dataFetchingEnvironment) {
        return dataFetchingEnvironment.getArgument("name");
    }

    public List<Ingredient> getAllValues() {
        return StaticData.getIngredients();
    }

    public Ingredient findValueFromArg(String arg) {
        return StaticData.getIngredients().stream().filter(i -> i.getName().equals(arg)).findFirst().orElse(null);
    }

    public DataFetcher getIngredients() {
        return dataFetchingEnvironment -> PlanitFoodDataFetcher.valueFetcher(this, dataFetchingEnvironment);
    }

    public DataFetcher getIngredientsForDish() {
        return dataFetchingEnvironment -> {
            Dish dish = dataFetchingEnvironment.getSource();
            return dish.getIngredients();
        };
    }
}
