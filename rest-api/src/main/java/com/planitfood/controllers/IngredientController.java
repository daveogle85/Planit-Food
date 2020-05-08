package com.planitfood.controllers;

import com.planitfood.data.IngredientsDataHandler;
import com.planitfood.exceptions.EntityNotFoundException;
import com.planitfood.models.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ComponentScan({"com.planitfood.data"})
public class IngredientController {

    private IngredientsDataHandler ingredientsDataHandler;

    @Autowired
    public IngredientController(IngredientsDataHandler ingredientsDataHandler) {
        this.ingredientsDataHandler = ingredientsDataHandler;
    }

    @GetMapping("/ingredients")
    List<Ingredient> search(
            @RequestParam(required = false) String searchString
    ) throws Exception {
        try {
            if (searchString == null) {
                return ingredientsDataHandler.getAllIngredients();
            }
            return ingredientsDataHandler.findIngredientsBeginningWith(searchString);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/ingredients/{id}")
    Ingredient one(@PathVariable String id) throws EntityNotFoundException {
        try {
            return ingredientsDataHandler.getIngredientById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("ingredient", id);
        }
    }

    @PostMapping("/ingredients")
    Ingredient newIngredient(@RequestBody Ingredient newIngredient) {
        try {
            ingredientsDataHandler.addIngredient(newIngredient);
            return newIngredient;
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/ingredients")
    Ingredient replaceIngredient(@RequestBody Ingredient newIngredient) throws Exception {
        try {
            ingredientsDataHandler.updateIngredient(newIngredient);
            return newIngredient;
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/ingredients/{id}")
    void deleteIngredient(@PathVariable String id) {
        ingredientsDataHandler.deleteIngredient(id);
    }
}
