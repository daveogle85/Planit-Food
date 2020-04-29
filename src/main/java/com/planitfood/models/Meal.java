package com.planitfood.models;

import java.util.ArrayList;

public class Meal {

    String id;
    String name;
    Dish main;
    ArrayList<Dish> sides;
    String notes;

    public Meal(String id) {
        this.id = id;
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

    public Dish getMain() {
        return main;
    }

    public void setMain(Dish main) {
        this.main = main;
    }

    public ArrayList<Dish> getSides() {
        return sides;
    }

    public void setSides(ArrayList<Dish> sides) {
        this.sides = sides;
    }

    public void addSide(Dish side) {
        if (this.sides == null) {
            this.sides = new ArrayList<>();
        }

        this.sides.add(side);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
