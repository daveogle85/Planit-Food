package com.planitfood.mutations;

import com.planitfood.models.Day;
import com.planitfood.models.Meal;
import graphql.schema.DataFetcher;

import java.time.LocalDate;

public class DayMutation {
    public DataFetcher addDay() {
        return dataFetchingEnvironment -> {
            LocalDate date = LocalDate.parse(dataFetchingEnvironment.getArgument("date"));
            String mealId = dataFetchingEnvironment.getArgument("meal");
            String notes = dataFetchingEnvironment.getArgument("notes");
            Meal dayMeal = new Meal(mealId, "name_of_" + mealId);

            Day day = new Day(date, dayMeal);
            if (notes != null) day.setNotes(notes);
            return addDayToDatabase(day);
        };
    }

    private Day addDayToDatabase(Day day) {
        System.out.println("Added " + day.toString() + " to database");
        return day;
    }
}
