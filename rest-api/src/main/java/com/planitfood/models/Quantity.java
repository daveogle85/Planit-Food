package com.planitfood.models;

import com.planitfood.enums.Unit;
import lombok.Data;

@Data
public class Quantity {
    private double quantity;
    private Unit unit;

    public Quantity() {
        this.quantity = 0.0f;
        this.unit = Unit.UNIT;
    }

    public Quantity(double quantity, Unit unit) {
        this.quantity = quantity;
        this.unit = unit;
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
}
