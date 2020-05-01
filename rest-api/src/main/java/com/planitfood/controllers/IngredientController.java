package com.planitfood.controllers;

import com.planitfood.data.StaticData;
import com.planitfood.exceptions.IngredientNotFoundException;
import com.planitfood.models.Ingredient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IngredientController {

    @GetMapping("/ingredients")
    List<Ingredient> all() {
        return StaticData.getIngredients();
    }

    @PostMapping("/ingredients")
    Ingredient newIngredient(@RequestBody Ingredient newIngredient) {
        System.out.println("new Ingredient added");
        return newIngredient;
    }

    @GetMapping("/ingredients/{name}")
    Ingredient one(@PathVariable String name) throws IngredientNotFoundException {
        return StaticData.getIngredients().stream()
                .filter(i -> i.getName().toLowerCase()
                .equals(name.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IngredientNotFoundException(name));
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
