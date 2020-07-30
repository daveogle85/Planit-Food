package com.planitfood.controllers;

import com.planitfood.data.DishDataHandler;
import com.planitfood.enums.DishType;
import com.planitfood.models.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ComponentScan({"com.planitfood.data"})
public class DishController {

    @Autowired
    private DishDataHandler dishDataHandler;

    @GetMapping("/dishes/{id}")
    Dish one(@PathVariable String id) throws Exception {
        try {
            return dishDataHandler.getDishById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/dishes")
    List<Dish> search(
            @RequestParam(required = false) String searchName,
            @RequestParam(required = false) String ingredientId,
            @RequestParam(required = false) DishType dishType,
            @RequestParam(required = false) Boolean includeIngredients
    ) throws Exception {
        try {
            boolean withIngredients = includeIngredients != null && includeIngredients;
            if (searchName == null && ingredientId == null && dishType == null) {
                return dishDataHandler.getAllDishes(withIngredients);
            }
            return dishDataHandler.getDishesByQuery(searchName, ingredientId, dishType, withIngredients);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/dishes")
    Dish newDish(@RequestBody Dish newDish) throws Exception {
        try {
            newDish = dishDataHandler.addDish(newDish);
            return newDish;
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/dishes")
    Dish replaceDish(@RequestBody Dish newDish) throws Exception {
        try {
            newDish = dishDataHandler.updateDish(newDish);
            return newDish;
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/dishes/{id}")
    String deleteDish(@PathVariable String id) throws Exception {
        try {
            dishDataHandler.deleteDish(id);
            return id + " deleted";
        } catch (Exception e) {
            throw e;
        }
    }
}
