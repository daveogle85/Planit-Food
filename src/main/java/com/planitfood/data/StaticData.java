package com.planitfood.data;

import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import com.planitfood.models.Meal;

import java.util.ArrayList;
import java.util.List;

public class StaticData {
// TODO DELETE, these static lists will be a connection to the database

//    private static final List<Map<String, String>> books = Arrays.asList(
//            ImmutableMap.of("id",
//                    "book-1",
//                    "name",
//                    "Harry Potter and the Philosopher's Stone",
//                    "pageCount",
//                    "223",
//                    "authorId",
//                    "author-1"),
//            ImmutableMap.of("id",
//                    "book-2",
//                    "name",
//                    "Moby Dick",
//                    "pageCount",
//                    "635",
//                    "authorId",
//                    "author-2"),
//            ImmutableMap.of("id",
//                    "book-3",
//                    "name",
//                    "Interview with the vampire",
//                    "pageCount",
//                    "371",
//                    "authorId",
//                    "author-3")
//    );
//
//    private static final List<Map<String, String>> authors = Arrays.asList(
//            ImmutableMap.of("id",
//                    "author-1",
//                    "firstName",
//                    "Joanne",
//                    "lastName",
//                    "Rowling"),
//            ImmutableMap.of("id",
//                    "author-2",
//                    "firstName",
//                    "Herman",
//                    "lastName",
//                    "Melville"),
//            ImmutableMap.of("id",
//                    "author-3",
//                    "firstName",
//                    "Anne",
//                    "lastName",
//                    "Rice")
//    );

    public static List<Ingredient> getIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Ingredient potato = new Ingredient("Potato");
        Ingredient beans = new Ingredient("Beans");
        Ingredient carrot = new Ingredient("Carrot");
        ingredients.add(potato);
        ingredients.add(beans);
        ingredients.add(carrot);
        return ingredients;
    }

    public static List<Dish> getDishes() {
        ArrayList<Dish> dishes = new ArrayList<>();
        Dish chips = new Dish("dish-1", "Chips");
        Dish bakedBeans = new Dish("dish-2", "Baked Beans");
        Dish pie = new Dish("dish-3", "Chicken Pie");
        chips.addIngredient(new Ingredient("Potato"));
        bakedBeans.addIngredient(new Ingredient("Beans"));
        dishes.add(chips);
        dishes.add(bakedBeans);
        dishes.add(pie);
        return dishes;
    }

    public static List<Meal> getMeals() {
        ArrayList<Meal> meals = new ArrayList<>();
        Meal mealOne = new Meal("meal-1");
        List<Dish> dishes = getDishes();
        mealOne.setMain(dishes.get(2));
        mealOne.addSide(dishes.get(1));
        mealOne.addSide(dishes.get(0));
        Dish hotpot = new Dish("dish-4", "Hotpot");
        Meal mealTwo = new Meal("meal-2");
        mealTwo.setMain(hotpot);
        meals.add(mealOne);
        meals.add(mealTwo);
        return meals;
    }
}
