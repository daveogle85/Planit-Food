package com.planitfood.mealdetails;

import com.planitfood.data.StaticData;
import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GraphQLDataFetchers {
    public DataFetcher getIngredients() {
        return dataFetchingEnvironment -> {
            String ingredientName = dataFetchingEnvironment.getArgument("name");
            if (ingredientName == null) {
                return StaticData.getIngredients();
            }

            Ingredient ingredient = StaticData.getIngredients().stream().filter(i -> i.getName().equals(ingredientName)).findFirst().orElse(null);
            if (ingredient == null) {
                return null;
            }
            List<Ingredient> result = new ArrayList();
            result.add(ingredient);
            return result;
        };
    }

    public DataFetcher getDishes() {
        return dataFetchingEnvironment -> {
            String dishId = dataFetchingEnvironment.getArgument("id");
            if (dishId == null) {
                return StaticData.getDishes();
            }

            Dish dish = StaticData.getDishes().stream().filter(d -> d.getId().equals(dishId)).findFirst().orElse(null);
            if (dish == null) {
                return null;
            }
            List<Dish> foundDish = new ArrayList();
            foundDish.add(dish);
            return foundDish;
        };
    }

    public DataFetcher getIngredientsForDish() {
        return dataFetchingEnvironment -> {
            Dish dish = dataFetchingEnvironment.getSource();
            return dish.getIngredients();
        };
    }

// Old code
//    public DataFetcher getBookByIdDataFetcher() {
//
//        List<Map<String, String>> books = StaticData.getBooks();
//        return dataFetchingEnvironment -> {
//            String bookId = dataFetchingEnvironment.getArgument("id");
//            return books
//                    .stream()
//                    .filter(book -> book.get("id").equals(bookId))
//                    .findFirst()
//                    .orElse(null);
//        };
//    }
//
//    public DataFetcher getAuthorDataFetcher() {
//        List<Map<String, String>> authors = StaticData.getAuthors();
//        return dataFetchingEnvironment -> {
//            Map<String, String> book = dataFetchingEnvironment.getSource();
//            String authorId = book.get("authorId");
//            return authors
//                    .stream()
//                    .filter(author -> author.get("id").equals(authorId))
//                    .findFirst()
//                    .orElse(null);
//        };
//    }
}
