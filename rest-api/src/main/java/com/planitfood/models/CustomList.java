package com.planitfood.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.planitfood.typeConverters.MealListTypeConverter;

import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName = "List")
public class CustomList {
    private String id;
    private String name;
    private List<Meal> meals;

    public CustomList() {
    }

    public CustomList(String id) {
        this.id = id;
    }

    public CustomList(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @DynamoDBHashKey(attributeName = "ID")
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    @DynamoDBHashKey(attributeName = "ID")
    @DynamoDBAutoGeneratedKey
    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "ListName")
    public String getName() {
        return name;
    }

    @DynamoDBAttribute(attributeName = "ListName")
    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBTypeConverted(converter = MealListTypeConverter.class)
    @DynamoDBAttribute(attributeName = "MealList")
    public List<Meal> getMeals() {
        return meals;
    }

    @DynamoDBTypeConverted(converter = MealListTypeConverter.class)
    @DynamoDBAttribute(attributeName = "MealList")
    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @DynamoDBIgnore
    public void addMeal(Meal meal) {
        if (this.meals == null) {
            this.meals = new ArrayList<>();
        }
        this.meals.add(meal);
    }
}