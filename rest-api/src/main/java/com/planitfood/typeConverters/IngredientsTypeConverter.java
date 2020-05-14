package com.planitfood.typeConverters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.planitfood.models.Ingredient;

import java.util.List;
import java.util.stream.Collectors;

public class IngredientsTypeConverter implements DynamoDBTypeConverter<List<String>, List<Ingredient>> {
    @Override
    public List<String> convert(List<Ingredient> ingredients) {
    return ingredients.stream().map(i -> i.getId()).collect(Collectors.toList());
    }

    @Override
    public List<Ingredient> unconvert(List<String> ingredientList) {
        return ingredientList.stream().map(i -> new Ingredient(i)).collect(Collectors.toList());
    }
}
