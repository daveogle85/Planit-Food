package com.planitfood.models;

import com.planitfood.enums.Unit;
import lombok.Data;

@Data
public class Quantity {
    private float quantity;
    private Unit unit;

    public Quantity() {
        this.quantity = 0.0f;
        this.unit = Unit.UNIT;
    }
}
