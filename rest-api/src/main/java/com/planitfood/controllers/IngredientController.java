package com.planitfood.controllers;

import com.planitfood.data.IngredientsDataHandler;
import com.planitfood.data.StaticData;
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
    List<Ingredient> search(@RequestParam String searchString) {
        return StaticData.getIngredients();
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
            ingredientsDataHandler.addIngredient(newIngredient);
            return newIngredient;
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/ingredients/{name}")
    Ingredient replaceIngredient(@RequestBody Ingredient newIngredient, @PathVariable String name) {
        System.out.println("Did a put with " + name);
        return new Ingredient(name);
    }

    @DeleteMapping("/ingredients/{name}")
    void deleteIngredient(@PathVariable String name) {
        System.out.println("Deleted " + name);
    }
}
