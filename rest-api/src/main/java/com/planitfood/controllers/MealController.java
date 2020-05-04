package com.planitfood.controllers;

import com.planitfood.data.StaticData;
import com.planitfood.exceptions.EntityNotFoundException;
import com.planitfood.models.Meal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MealController {
    @GetMapping("/meals")
    List<Meal> all() {
        return StaticData.getMeals();
    }

    @GetMapping("/meals/{id}")
    Meal one(@PathVariable String id) throws EntityNotFoundException {
        return StaticData.getMeals().stream()
                .filter(i -> i.getId().toLowerCase()
                        .equals(id.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("meal", id));
    }

    @PostMapping("/meals")
    Meal newMeal(@RequestBody Meal newMeal) {
        System.out.println("new Meal added");
        return newMeal;
    }

    @PutMapping("/meals/{id}")
    Meal replaceMeal(@RequestBody Meal newMeal, @PathVariable String id) {
        System.out.println("Did a put with " + id);
        return new Meal(id, id);
    }

    @DeleteMapping("/meals/{id}")
    void deleteMeal(@PathVariable String id) {
        System.out.println("Deleted " + id);
    }
}
