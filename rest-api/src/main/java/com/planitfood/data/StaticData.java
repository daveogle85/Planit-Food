package com.planitfood.data;

import com.planitfood.enums.DishType;
import com.planitfood.models.Day;
import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import com.planitfood.models.Meal;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class StaticData {
    public static List<Dish> getDishes() {
        ArrayList<Dish> dishes = new ArrayList<>();
        Dish chips = new Dish("dish-1", "Chips");
        Dish bakedBeans = new Dish("dish-2", "Baked Beans");
        Dish pie = new Dish("dish-3", "Chicken Pie");
        chips.addIngredient(new Ingredient("i1", "Potato"));
        bakedBeans.addIngredient(new Ingredient("i2", "Beans"));
        chips.setDishType(DishType.SIDE);
        bakedBeans.setDishType(DishType.SIDE);
        pie.setDishType(DishType.MAIN);
        dishes.add(chips);
        dishes.add(bakedBeans);
        dishes.add(pie);
        return dishes;
    }

    public static List<Meal> getMeals() {
        ArrayList<Meal> meals = new ArrayList<>();
        Meal mealOne = new Meal("meal-1", "My first meal");
        List<Dish> dishes = getDishes();
        mealOne.addDish(dishes.get(2));
        mealOne.addDish(dishes.get(1));
        mealOne.addDish(dishes.get(0));
        Dish hotpot = new Dish("dish-4", "Hotpot");
        Meal mealTwo = new Meal("meal-2", "Hotpot");
        mealTwo.addDish(hotpot);
        meals.add(mealOne);
        meals.add(mealTwo);
        return meals;
    }

    public static List<Day> getDays(LocalDate startDate, LocalDate endDate) {
        ArrayList<Day> days = new ArrayList<>();
        List<Meal> meals = getMeals();
        days.add(new Day(startDate, meals.get(0)));

        if (endDate == null) {
            endDate = startDate;
        }

        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);

        for (int i = 0; i < numberOfDays; i++) {
            Day day = new Day(startDate, meals.get(i % 2));
            days.add(day);
        }
        return days;
    }
}
