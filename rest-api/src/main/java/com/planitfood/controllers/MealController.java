package com.planitfood.controllers;

import com.planitfood.data.MealDataHandler;
import com.planitfood.models.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ComponentScan({"com.planitfood.data"})
public class MealController {

    @Autowired
    private MealDataHandler mealDataHandler;

    @GetMapping("/meals")
    List<Meal> search(
            @RequestParam(required = false) String searchName,
            @RequestParam(required = false) Boolean includeDishes
    ) throws Exception {
        try {
            boolean withDishes = includeDishes != null && includeDishes;
            if (searchName == null) {
                return mealDataHandler.getAllMeals(withDishes);
            }
            return mealDataHandler.getMealsByQuery(searchName, null, withDishes);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/meals/{id}")
    Meal one(@PathVariable String id) throws Exception {
        try {
            return mealDataHandler.getMealById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/meals")
    Meal newMeal(@RequestBody Meal newMeal) throws Exception {
        try {
            newMeal = mealDataHandler.addMeal(newMeal);
            return newMeal;
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/meals")
    Meal replaceMeal(@RequestBody Meal newMeal) throws Exception {
        try {
            newMeal = mealDataHandler.updateMeal(newMeal);
            return newMeal;
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/meals/{id}")
    String deleteMeal(@PathVariable String id) throws Exception {
        try {
            mealDataHandler.deleteMeal(id);
            return id + " deleted";
        } catch (Exception e) {
            throw e;
        }
    }
}
