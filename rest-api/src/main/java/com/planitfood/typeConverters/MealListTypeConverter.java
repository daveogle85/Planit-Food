package com.planitfood.typeConverters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.planitfood.models.Meal;

import java.util.List;
import java.util.stream.Collectors;

public class MealListTypeConverter implements DynamoDBTypeConverter<List<String>, List<Meal>> {
    @Override
    public List<String> convert(List<Meal> meals) {
        return meals.stream().map(i -> i.getId()).collect(Collectors.toList());
    }

    @Override
    public List<Meal> unconvert(List<String> mealList) {
        return mealList.stream().map(i -> new Meal(i)).collect(Collectors.toList());
    }
}
