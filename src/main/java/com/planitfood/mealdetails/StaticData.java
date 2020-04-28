package com.planitfood.mealdetails;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StaticData {
    // TODO DELETE, these static lists will be a connection to the database
    private static final List<Map<String, String>> books = Arrays.asList(
            ImmutableMap.of("id",
                    "book-1",
                    "name",
                    "Harry Potter and the Philosopher's Stone",
                    "pageCount",
                    "223",
                    "authorId",
                    "author-1"),
            ImmutableMap.of("id",
                    "book-2",
                    "name",
                    "Moby Dick",
                    "pageCount",
                    "635",
                    "authorId",
                    "author-2"),
            ImmutableMap.of("id",
                    "book-3",
                    "name",
                    "Interview with the vampire",
                    "pageCount",
                    "371",
                    "authorId",
                    "author-3")
    );

    private static final List<Map<String, String>> authors = Arrays.asList(
            ImmutableMap.of("id",
                    "author-1",
                    "firstName",
                    "Joanne",
                    "lastName",
                    "Rowling"),
            ImmutableMap.of("id",
                    "author-2",
                    "firstName",
                    "Herman",
                    "lastName",
                    "Melville"),
            ImmutableMap.of("id",
                    "author-3",
                    "firstName",
                    "Anne",
                    "lastName",
                    "Rice")
    );

    private static final List<Map<String, String>> ingredients = Arrays.asList(
            ImmutableMap.of(
                    "name", "potato"
            ),
            ImmutableMap.of(
                    "name", "beans"
            ));

//    private static final List<Map<String, String>> dishes = Arrays.asList(
//            ImmutableMap.of(
//                    "id", "1",
//                    "name", "chips",
//                    "ingredientIds", ['']
//            ),
//            ImmutableMap.of(
//                    "name", "beans"
//            ));

    public static List<Map<String, String>> getIngredients() {
        return ingredients;
    }

    public static List<Map<String, String>> getBooks() {
        return books;
    }

    public static List<Map<String, String>> getAuthors() {
        return authors;
    }
}
