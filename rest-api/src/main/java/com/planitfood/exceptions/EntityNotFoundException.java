package com.planitfood.exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String entityName, String id) {
        super("The " + entityName + " " + id + " was not found");
    }
}

