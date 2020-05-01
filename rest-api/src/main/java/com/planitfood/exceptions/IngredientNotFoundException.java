package com.planitfood.exceptions;

public class IngredientNotFoundException extends Exception {
    public IngredientNotFoundException(String name) {
        super("The ingredient " + name + " was not found");
    }
}
