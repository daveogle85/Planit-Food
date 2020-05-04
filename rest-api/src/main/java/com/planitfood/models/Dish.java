package com.planitfood.models;

import com.planitfood.enums.DishType;

import java.util.ArrayList;

public class Dish {
    private String id;
    private String name;
    private DishType dishType;
    private ArrayList<Ingredient> ingredients;
    private String notes;
    private Float cookingTime;

    public Dish(String id, String name) {
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

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        if (this.ingredients == null) {
            this.ingredients = new ArrayList();
        }
        this.ingredients.add(ingredient);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Float getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Float cookingTime) {
        this.cookingTime = cookingTime;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }
}
