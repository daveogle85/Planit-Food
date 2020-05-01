package com.planitfood.models;

import com.planitfood.enums.Unit;

import lombok.Data;

@Data
public class Ingredient {

    private String name;
    private float quantity = 0.0f;
    private Unit unit = Unit.UNIT;

    public Ingredient(String name) {
        this.name = name;
    }
}
