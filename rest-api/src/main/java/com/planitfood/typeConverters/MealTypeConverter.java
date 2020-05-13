package com.planitfood.typeConverters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.planitfood.models.Meal;

public class MealTypeConverter implements DynamoDBTypeConverter<String, Meal> {
    @Override
    public String convert(Meal meal) {
        return meal.getId();
    }

    @Override
    public Meal unconvert(String meal) {
        return new Meal(meal);
    }
}
