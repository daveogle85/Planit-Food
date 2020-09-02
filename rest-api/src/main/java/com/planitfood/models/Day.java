package com.planitfood.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.planitfood.typeConverters.LocalDateTypeConverter;
import com.planitfood.typeConverters.MealTypeConverter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Month;

@DynamoDBTable(tableName = "Day")
public class Day {

    private String id;
    @NotNull
    private LocalDate date;
    private MealWithFullDish meal;
    private String notes;

    public Day() {
    }

    public Day(LocalDate date) {
        this.id = createId(date);
        this.date = date;
    }

    public Day(LocalDate date, MealWithFullDish meal) {
        this.id = createId(date);
        this.date = date;
        this.meal = meal;
    }

    @DynamoDBHashKey(attributeName = "ID")
    public String getId() {
        return id;
    }

    @DynamoDBHashKey(attributeName = "ID")
    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBRangeKey(attributeName = "DayDate")
    @DynamoDBTypeConverted(converter = LocalDateTypeConverter.class)
    public LocalDate getDate() {
        return date;
    }

    @DynamoDBRangeKey(attributeName = "DayDate")
    @DynamoDBTypeConverted(converter = LocalDateTypeConverter.class)
    public void setDate(LocalDate date) {
        this.date = date;
        this.id = createId(date);
    }

    @DynamoDBTypeConvertedJson
    @DynamoDBAttribute(attributeName = "Meal")
    public MealWithFullDish getMeal() {
        return meal;
    }

    @DynamoDBTypeConvertedJson
    @DynamoDBAttribute(attributeName = "Meal")
    public void setMeal(MealWithFullDish meal) {
        this.meal = meal;
    }

    @DynamoDBAttribute(attributeName = "Notes")
    public String getNotes() {
        return notes;
    }

    @DynamoDBAttribute(attributeName = "Notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Create an id that is the month and year in the format
     * March-2020
     *
     * @param date
     * @return
     */
    public static String createId(LocalDate date) {
        Month month = date.getMonth();
        int year = date.getYear();
        return month.name() + "-" + year;
    }
}
