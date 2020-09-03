package com.planitfood.models;

import com.planitfood.enums.Unit;
import lombok.Data;

@Data
public class Quantity {
    private double quantity;
    private Unit unit;
    private String ingredientId;

    public Quantity() {
        this.quantity = 0.0f;
        this.unit = Unit.UNIT;
        this.ingredientId = "";
    }

    public Quantity(double quantity, Unit unit, String ingredientId) {
        this.quantity = quantity;
        this.unit = unit;
        this.ingredientId = ingredientId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getIngredientId() {
        return this.ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }
}
