package com.planitfood.typeConverters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.planitfood.models.Dish;

import java.util.List;
import java.util.stream.Collectors;

public class DishTypeConverter implements DynamoDBTypeConverter<List<String>, List<Dish>> {
    @Override
    public List<String> convert(List<Dish> dishes) {
        return dishes.stream().map(i -> i.getId()).collect(Collectors.toList());
    }

    @Override
    public List<Dish> unconvert(List<String> dishList) {
        return dishList.stream().map(i -> new Dish(i)).collect(Collectors.toList());
    }
}