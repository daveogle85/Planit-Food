package com.planitfood.models;

import java.time.LocalDate;

public class Day {

    LocalDate date;
    Meal meal;
    String notes;

    public Day(LocalDate date, Meal meal) {
        this.date = date;
        this.meal = meal;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
