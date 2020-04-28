package com.planitfood.mealdetails;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers {

    public DataFetcher getIngredientByNameDataFetcher() {
        List<Map<String, String>> ingredients = StaticData.getIngredients();
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            return ingredients.stream().filter(i -> i.get("name").equals(name)).findFirst().orElse(null);
        };
    }

    public DataFetcher getIngredients() {
        List<Map<String, String>> ingredients = StaticData.getIngredients();
        return DataFetchingEnvironment -> ingredients;
    }

    public DataFetcher getBookByIdDataFetcher() {

        List<Map<String, String>> books = StaticData.getBooks();
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");
            return books
                    .stream()
                    .filter(book -> book.get("id").equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getAuthorDataFetcher() {
        List<Map<String, String>> authors = StaticData.getAuthors();
        return dataFetchingEnvironment -> {
            Map<String, String> book = dataFetchingEnvironment.getSource();
            String authorId = book.get("authorId");
            return authors
                    .stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }
}
