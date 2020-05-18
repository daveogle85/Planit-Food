package com.planitfood.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTransactionLoadExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.planitfood.exceptions.EntityNotFoundException;
import com.planitfood.models.Day;
import com.planitfood.models.Dish;
import com.planitfood.models.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MealDataHandler {

    @Autowired
    private DayDataHandler dayDataHandler;

    @Autowired
    private DishDataHandler dishDataHandler;

    @Autowired
    DynamoDB dynamoDB;


    public List<Meal> getAllMeals() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

        List<Meal> results = dynamoDB.getMapper().scan(Meal.class, scanExpression);
        return results.stream().map(r -> addDishesToMeal(r)).collect(Collectors.toList());
    }

    public Meal getMealById(String id) throws Exception {
        Meal found = dynamoDB.getMapper().load(Meal.class, id);

        if (found != null) {
            return addDishesToMeal(found);
        } else {
            throw new EntityNotFoundException("meal", id);
        }
    }

    public void addMeal(Meal meal) throws Exception {
        dynamoDB.getMapper().save(meal);
    }

    public void updateMeal(Meal meal) throws Exception {
        Meal found = dynamoDB.getMapper().load(Meal.class, meal.getId());

        if (found != null) {
            dynamoDB.getMapper().save(meal);
        } else {
            throw new EntityNotFoundException("meal", meal.getId());
        }
    }

    public void deleteMeal(String id) {
        final Meal toDelete = new Meal(id);
        List<Day> found = dayDataHandler.getDaysByQuery(id, false);
        TransactionWriteRequest transactionWriteRequest = new TransactionWriteRequest();
        transactionWriteRequest.addDelete(toDelete);
        if (found != null & found.size() > 0) {
            found.forEach(day -> {
                day.setMeal(null);
                transactionWriteRequest.addUpdate(day);
            });
        }
        Transactions.executeTransactionWrite(transactionWriteRequest, dynamoDB.getMapper());
    }

    public List<Meal> getMealsByQuery(String searchName, String dishId, boolean addDishes) {
        Map<String, AttributeValue> eav = new HashMap();
        final String searchNameQuery = "contains(SearchName, :val1)";
        final String dishIdQuery = "contains(Dishes, :val2)";
        String queryString = "";

        if (searchName != null) {
            eav.put(":val1", new AttributeValue().withS(searchName.toLowerCase()));
            queryString += searchNameQuery;
        }

        if (dishId != null) {
            eav.put(":val2", new AttributeValue().withS(dishId));
            if (queryString.isEmpty()) {
                queryString += dishIdQuery;
            } else {
                queryString += " and " + dishIdQuery;
            }
        }

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(queryString)
                .withExpressionAttributeValues(eav);

        List<Meal> results = scanMeals(scanExpression);

        if(!addDishes) {
            return results;
        }
        return results.stream().map(r -> addDishesToMeal(r)).collect(Collectors.toList());
    }

    private List<Meal> scanMeals(DynamoDBScanExpression scanExpression) {
        List<Meal> matchedDishes = dynamoDB.getMapper().scan(Meal.class, scanExpression);
        return matchedDishes;
    }

    private Meal addDishesToMeal(Meal meal) {
        TransactionLoadRequest transactionLoadRequest = new TransactionLoadRequest();
        List<Dish> mealDishes = meal.getDishes();

        if (mealDishes == null) {
            return meal;
        }

        mealDishes.forEach(dish -> transactionLoadRequest.addLoad(dish));
        List<Dish> results = Transactions.executeTransactionLoad(transactionLoadRequest, dynamoDB.getMapper());
        results = results.stream().map(dish -> dishDataHandler.addIngredientsToDish(dish)).collect(Collectors.toList());
        meal.setDishes(results);
        return meal;
    }

    public Meal addDishIdAndNameToMeal(Meal meal) {
        TransactionLoadRequest transactionLoadRequest = new TransactionLoadRequest();
        List<Dish> mealDishes = meal.getDishes();

        if (mealDishes == null) {
            return meal;
        }

        mealDishes.forEach(dish -> {
            DynamoDBTransactionLoadExpression loadExpressionForDish = new DynamoDBTransactionLoadExpression();
            transactionLoadRequest.addLoad(dish, loadExpressionForDish);
        });
        List<Dish> results = Transactions.executeTransactionLoad(transactionLoadRequest, dynamoDB.getMapper());
        meal.setDishes(results);
        return meal;
    }
}
