package com.planitfood.mealdetails;

import com.planitfood.data.StaticData;
import com.planitfood.models.Dish;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DishDataFetcher implements IDataFetcher {
    public String getArgument(DataFetchingEnvironment dataFetchingEnvironment) {
        return dataFetchingEnvironment.getArgument("id");
    }

    public List<Dish> getAllValues() {
        return StaticData.getDishes();
    }

    public Dish findValueFromArg(String arg) {
        return StaticData.getDishes().stream().filter(d -> d.getId().equals(arg)).findFirst().orElse(null);
    }

    public DataFetcher getDishes() {
        return dataFetchingEnvironment -> PlanitFoodDataFetcher.valueFetcher(this, dataFetchingEnvironment);
    }
}
