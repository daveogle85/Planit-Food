package com.planitfood.models;

import java.util.ArrayList;

public class Meal {

    String id;
    String name;
    ArrayList<Dish> dishes;
    String notes;

    public Meal(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public void addDish(Dish dish) {
        if (this.dishes == null) {
            this.dishes = new ArrayList<>();
        }

        this.dishes.add(dish);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
