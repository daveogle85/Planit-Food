package com.planitfood.controllers;

import com.planitfood.data.ListDataHandler;
import com.planitfood.models.CustomList;
import com.planitfood.models.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ComponentScan({"com.planitfood.data"})
public class ListController {

    @Autowired
    ListDataHandler listDataHandler;

    @GetMapping("/lists/id/{id}")
    CustomList oneById(@PathVariable String id) throws Exception {
        try {
            return listDataHandler.getListById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/lists/name/{name}")
    CustomList oneByName(@PathVariable String name) throws Exception {
        try {
            return listDataHandler.getListByName(name, true);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/lists")
    List<CustomList> all() throws Exception {
        try {
            return listDataHandler.getAllLists();
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/lists")
    CustomList newList(@RequestBody CustomList newList) throws Exception {
        try {
            CustomList addedList = listDataHandler.addList(newList);
            return addedList;
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/lists/{id}/meals")
    CustomList updateMeals(@PathVariable String id, @RequestBody Meal mealToAdd) throws Exception {
        try {
            CustomList updatedList = listDataHandler.updateMealsInList(id, mealToAdd);
            return updatedList;
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/lists/{id}")
    String deleteList(@PathVariable String id) throws Exception {
        try {
            listDataHandler.deleteList(id);
            return id + " deleted";
        } catch (Exception e) {
            throw e;
        }
    }

}
