package com.planitfood.data;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.planitfood.exceptions.EntityNotFoundException;
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
    private DishDataHandler dishDataHandler;
    private final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.EU_WEST_2)
            .build();
    private final DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(this.client);

    public List<Meal> getAllMeals() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

        List<Meal> results = dynamoDBMapper.scan(Meal.class, scanExpression);
        return results.stream().map(r -> addDishToMeal(r)).collect(Collectors.toList());
    }

    public Meal getMealById(String id) throws Exception {
        Meal found = dynamoDBMapper.load(Meal.class, id);

        if (found != null) {
            return addDishToMeal(found);
        } else {
            throw new EntityNotFoundException("meal", id);
        }
    }

    public void addMeal(Meal meal) throws Exception {
        dynamoDBMapper.save(meal);
    }

    public void updateMeal(Meal meal) throws Exception {
        Meal found = dynamoDBMapper.load(Meal.class, meal.getId());

        if (found != null) {
            dynamoDBMapper.save(meal);
        } else {
            throw new EntityNotFoundException("meal", meal.getId());
        }
    }

    public void deleteMeal(String id) {
        final Meal toDelete = new Meal(id);
        this.dynamoDBMapper.delete(toDelete);
    }

    public List<Meal> getMealsByQuery(String searchName, String dishId) {
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
        return results.stream().map(r -> addDishToMeal(r)).collect(Collectors.toList());
    }

    private List<Meal> scanMeals(DynamoDBScanExpression scanExpression) {
        List<Meal> matchedDishes = dynamoDBMapper.scan(Meal.class, scanExpression);
        return matchedDishes;
    }

    private Meal addDishToMeal(Meal meal) {
        TransactionLoadRequest transactionLoadRequest = new TransactionLoadRequest();
        List<Dish> mealDishes = meal.getDishes();

        if (mealDishes == null) {
            return meal;
        }

        mealDishes.forEach(dish -> transactionLoadRequest.addLoad(dish));
        List<Dish> results = Transactions.executeTransactionLoad(transactionLoadRequest, dynamoDBMapper);
        results = results.stream().map(dish -> dishDataHandler.addIngredientsToDish(dish)).collect(Collectors.toList());
        meal.setDishes(results);
        return meal;
    }
}
