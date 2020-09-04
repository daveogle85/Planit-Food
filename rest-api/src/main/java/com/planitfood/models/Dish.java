package com.planitfood.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.planitfood.enums.DishType;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Dish {
    private String id;
    @NotNull
    private String name;
    private String searchName;
    @NotNull
    private DishType dishType;
    private List<Ingredient> ingredients;
    private String notes;
    private Float cookingTime;
    @JsonIgnore
    private List<Quantity> quantities;

    public Dish() {
    }

    public Dish(String id) {
        this.id = id;
    }

    public Dish(String id, String name) {
        this.id = id;
        this.name = name;
        this.searchName = name.toLowerCase();
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
        this.searchName = name.toLowerCase();
    }

    public String getSearchName() {
        return searchName;
    }
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        if (this.ingredients == null) {
            this.ingredients = new ArrayList<>();
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

    @JsonIgnore
    public List<Quantity> getQuantities() {
        return this.quantities;
    }

    public void setQuantities(List<Quantity> quantities) {
        this.quantities = quantities;
    }
}
