package com.planitfood.data;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.planitfood.enums.DishType;
import com.planitfood.exceptions.EntityNotFoundException;
import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DishDataHandler {
    private final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.EU_WEST_2)
            .build();
    private final DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(this.client);

    public List<Dish> getAllDishes() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

        List<Dish> results = dynamoDBMapper.scan(Dish.class, scanExpression);
        return results.stream().map(r -> addIngredientsToDish(r)).collect(Collectors.toList());
    }

    public Dish getDishById(String id) throws Exception {
        Dish found = dynamoDBMapper.load(Dish.class, id);

        if (found != null) {
            return found;
        } else {
            throw new EntityNotFoundException("dish", id);
        }
    }

    public void addDish(Dish dish) throws Exception {
        dynamoDBMapper.save(dish);
    }

    public void updateDish(Dish dish) throws Exception {
        Dish found = dynamoDBMapper.load(Dish.class, dish.getId());

        if (found != null) {
            dynamoDBMapper.save(dish);
        } else {
            throw new EntityNotFoundException("dish", dish.getId());
        }
    }

    public void deleteDish(String id) {
        final Dish toDelete = new Dish(id);
        this.dynamoDBMapper.delete(toDelete);
    }

    public List<Dish> getDishesByQuery(String searchName, String ingredientId, DishType dishType) {
        Map<String, AttributeValue> eav = new HashMap();
        final String searchNameQuery = "contains(SearchName, :val1)";
        final String ingredientNameQuery = "contains(Ingredients, :val2)";
        final String dishTypeQuery = "DishType = :val3";
        String queryString = "";

        if (searchName != null) {
            eav.put(":val1", new AttributeValue().withS(searchName.toLowerCase()));
            queryString += searchNameQuery;
        }

        if (ingredientId != null) {
            eav.put(":val2", new AttributeValue().withS(ingredientId));
            if (queryString.isEmpty()) {
                queryString += ingredientNameQuery;
            } else {
                queryString += " and " + ingredientNameQuery;
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
        return results.stream().map(r -> addIngredientsToDish(r)).collect(Collectors.toList());
    }

    private List<Dish> scanDishes(DynamoDBScanExpression scanExpression) {
        List<Dish> matchedDishes = dynamoDBMapper.scan(Dish.class, scanExpression);
        return matchedDishes;
    }

    private Dish addIngredientsToDish(Dish dishById) {
        TransactionLoadRequest transactionLoadRequest = new TransactionLoadRequest();
        List<Ingredient> dishIngredients = dishById.getIngredients();

        if (dishIngredients == null) {
            return dishById;
        }

        dishIngredients.forEach(ingredient -> transactionLoadRequest.addLoad(ingredient));
        List<Ingredient> results = Transactions.executeTransactionLoad(transactionLoadRequest, dynamoDBMapper);
        dishById.setIngredients(results);
        return dishById;
    }
}
