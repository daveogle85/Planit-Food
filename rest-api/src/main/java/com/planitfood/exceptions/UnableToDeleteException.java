package com.planitfood.exceptions;

public class UnableToDeleteException extends Exception {

    public UnableToDeleteException(String id, String reason) {
        super("Could not delete " + id + " because " + reason);
    }
}


