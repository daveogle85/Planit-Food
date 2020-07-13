package com.planitfood.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.planitfood.enums.DishType;
import com.planitfood.exceptions.EntityNotFoundException;
import com.planitfood.exceptions.UnableToDeleteException;
import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import com.planitfood.models.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DishDataHandler {

    @Autowired
    private DynamoDB dynamoDB;

    @Autowired
    private MealDataHandler mealDataHandler;

    public List<Dish> getAllDishes(boolean withIngredients) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

        List<Dish> results = dynamoDB.getMapper().scan(Dish.class, scanExpression);

        if(withIngredients) {
            return results.stream().map(r -> addIngredientsToDish(r)).collect(Collectors.toList());
        }
        return results;
    }

    public Dish getDishById(String id) throws Exception {
        Dish found = dynamoDB.getMapper().load(Dish.class, id);

        if (found != null) {
            return addIngredientsToDish(found);
        } else {
            throw new EntityNotFoundException("dish", id);
        }
    }

    public List<Dish> addDishesToDatabase(List<Dish> dishes) throws Exception {
        List<Dish> updatedDishes = new ArrayList<>();
        for (Dish dish : dishes) {
            Dish foundDish = dish.getId() == null ? null : dynamoDB.getMapper().load(Dish.class, dish.getId());
            if (foundDish == null) {
                updatedDishes.add(addDish(dish));

            } else {
                updatedDishes.add(dish);
            }
        }
        return updatedDishes;
    }

    public Dish addDish(Dish dish) throws Exception {
        dynamoDB.getMapper().save(dish);
        return dish;
    }

    public Dish updateDish(Dish dish) throws Exception {
        Dish found = dynamoDB.getMapper().load(Dish.class, dish.getId());

        if (found != null) {
            dynamoDB.getMapper().save(dish);
            return dish;
        } else {
            throw new EntityNotFoundException("dish", dish.getId());
        }
    }

    public void deleteDish(String id) throws UnableToDeleteException {
        final Dish toDelete = new Dish(id);
        List<Meal> found = mealDataHandler.getMealsByQuery(null, id, false);
        if (found != null & found.size() > 0) {
            throw new UnableToDeleteException(id, "dish is used in meal " + found.get(0).getId());
        } else {
            this.dynamoDB.getMapper().delete(toDelete);
        }
    }

    public List<Dish> getDishesByQuery(String searchName, String ingredientId, DishType dishType, boolean addIngredients) {
        Map<String, AttributeValue> eav = new HashMap();
        final String searchNameQuery = "contains(SearchName, :val1)";
        final String ingredientIdQuery = "contains(Ingredients, :val2)";
        final String dishTypeQuery = "DishType = :val3";
        String queryString = "";

        if (searchName != null) {
            eav.put(":val1", new AttributeValue().withS(searchName.toLowerCase()));
            queryString += searchNameQuery;
        }

        if (ingredientId != null) {
            eav.put(":val2", new AttributeValue().withS(ingredientId));
            if (queryString.isEmpty()) {
                queryString += ingredientIdQuery;
            } else {
                queryString += " and " + ingredientIdQuery;
            }
        }

        if (dishType != null) {
            eav.put(":val3", new AttributeValue().withS(dishType.toString()));
            if (queryString.isEmpty()) {
                queryString += dishTypeQuery;
            } else {
                queryString += " and " + dishTypeQuery;
            }
        }

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(queryString)
                .withExpressionAttributeValues(eav);

        List<Dish> results = scanDishes(scanExpression);

        if (!addIngredients) {
            return results;
        }

        return results.stream().map(r -> addIngredientsToDish(r)).collect(Collectors.toList());
    }

    private List<Dish> scanDishes(DynamoDBScanExpression scanExpression) {
        List<Dish> matchedDishes = dynamoDB.getMapper().scan(Dish.class, scanExpression);
        return matchedDishes;
    }

    public Dish addIngredientsToDish(Dish dishById) {
        TransactionLoadRequest transactionLoadRequest = new TransactionLoadRequest();
        List<Ingredient> dishIngredients = dishById.getIngredients();

        if (dishIngredients == null) {
            return dishById;
        }

        dishIngredients.forEach(ingredient -> transactionLoadRequest.addLoad(ingredient));
        List<Ingredient> results = Transactions.executeTransactionLoad(transactionLoadRequest, dynamoDB.getMapper());
        dishById.setIngredients(results);
        return dishById;
    }
}
