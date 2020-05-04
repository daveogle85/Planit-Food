package com.planitfood.controllers;

import com.planitfood.data.StaticData;
import com.planitfood.exceptions.EntityNotFoundException;
import com.planitfood.models.Dish;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DishController {
    @GetMapping("/dishes")
    List<Dish> all() {
        return StaticData.getDishes();
    }

    @GetMapping("/dishes/{id}")
    Dish one(@PathVariable String id) throws EntityNotFoundException {
        return StaticData.getDishes().stream()
                .filter(i -> i.getId().toLowerCase()
                        .equals(id.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("dish", id));
    }

    @PostMapping("/dishes")
    Dish newDish(@RequestBody Dish newDish) {
        System.out.println("new Dish added");
        return newDish;
    }

    @PutMapping("/dishes/{id}")
    Dish replaceDish(@RequestBody Dish newDish, @PathVariable String id) {
        System.out.println("Did a put with " + id);
        return new Dish(id, id);
    }

    @DeleteMapping("/dishes/{id}")
    void deleteDish(@PathVariable String id) {
        System.out.println("Deleted " + id);
    }
}
