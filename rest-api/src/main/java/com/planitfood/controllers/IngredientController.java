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
    List<Ingredient> search(@RequestParam String searchString) throws Exception {
        try {
            return ingredientsDataHandler.findIngredientsBeginningWith(searchString);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/ingredients/{name}")
    Ingredient one(@PathVariable String name) throws EntityNotFoundException {
        try {
            return ingredientsDataHandler.getIngredientByName(name);
        } catch (Exception e) {
            throw new EntityNotFoundException("ingredient", name);
        }
    }

    @PostMapping("/ingredients")
    Ingredient newIngredient(@RequestBody Ingredient newIngredient) {
        try {
            ingredientsDataHandler.saveIngredient(newIngredient);
            return newIngredient;
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/ingredients/{name}")
    Ingredient replaceIngredient(@RequestBody Ingredient newIngredient, @PathVariable String name) {
        try {
            ingredientsDataHandler.saveIngredient(newIngredient);
            return newIngredient;
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/ingredients/{name}")
    void deleteIngredient(@PathVariable String name) {
        ingredientsDataHandler.deleteIngredient(name);
    }
}
