package com.planitfood.models;

import com.planitfood.enums.Unit;
import javax.validation.constraints.NotNull;

public class Ingredient {

    private String id;
    @NotNull
    private String name;
    private String searchName;
    private Double quantity;
    private Unit unit = Unit.UNIT;

    public Ingredient() {
    }

    public Ingredient(String id) {
        this.id = id;
    }

    public Ingredient(String id, String name) {
        this.id = id;
        this.name = name;
        this.searchName = name.toLowerCase();
    }

    public Ingredient(String id, String name, double quantity, Unit unit) {
        this.id = id;
        this.name = name;
        this.searchName = name.toLowerCase();
        this.quantity = quantity;
        this.unit = unit;
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

    public Double getQuantity() {
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
