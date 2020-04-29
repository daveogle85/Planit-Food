package com.planitfood.models;

import com.planitfood.mealdetails.Unit;

public class Ingredient {

    private String name;
    private float quantity = 0.0f;
    private Unit unit = Unit.UNIT;

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
