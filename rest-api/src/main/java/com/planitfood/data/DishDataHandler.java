package com.planitfood.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.planitfood.enums.DishType;
import com.planitfood.enums.EntityType;
import com.planitfood.exceptions.EntityNotFoundException;
import com.planitfood.exceptions.UnableToDeleteException;
import com.planitfood.models.*;
import com.planitfood.typeConverters.PlanitFoodEntityTypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DishDataHandler {

    private static Logger logger = LogManager.getLogger(DishDataHandler.class);

    @Autowired
    private DynamoDB dynamoDB;

    @Autowired
    private MealDataHandler mealDataHandler;

    @Autowired
    private IngredientsDataHandler ingredientsDataHandler;

    public List<Dish> getAllDishes(boolean withIngredients) {
        logger.info("Get all dishes");
        PlanitFoodEntity planitFoodEntity = new PlanitFoodEntity(EntityType.DISH);

        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(planitFoodEntity);

        List<PlanitFoodEntity> rawResults = dynamoDB.getMapper().query(PlanitFoodEntity.class, queryExpression);

        if (withIngredients) {
            return rawResults.stream()
                    .map(r -> addIngredientsToDish(r))
                    .collect(Collectors.toList());
        }

        List<Dish> results = rawResults.stream()
                .map((result) -> PlanitFoodEntityTypeConverter.convertToDish(result))
                .collect(Collectors.toList());

        logger.info("return all dishes: " + results.size());
        return results;

    }

    public Dish getDishById(String id) throws Exception {
        logger.info("Get dish id " + id);
        PlanitFoodEntity planitFoodEntity = new PlanitFoodEntity(EntityType.DISH);
        Map<String, AttributeValue> eav = new HashMap<>();
        final String query = "contains(SK, :val1)";
        eav.put(":val1", new AttributeValue().withS(id));
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(planitFoodEntity)
                .withFilterExpression(query)
                .withExpressionAttributeValues(eav);

        Optional found = dynamoDB.getMapper()
                .query(PlanitFoodEntity.class, queryExpression).stream().findFirst();


        if (found.isPresent()) {
            PlanitFoodEntity foundDish = (PlanitFoodEntity) found.get();
            logger.info("Return dish " + foundDish.getName());
            return addIngredientsToDish(foundDish);

        } else {
            throw new EntityNotFoundException("dish", id);
        }
    }

    public List<Dish> addDishesToDatabase(List<Dish> dishes) throws Exception {
        logger.info("add all dishes: " + dishes.size());
        List<Dish> updatedDishes = new ArrayList<>();
        for (Dish dish : dishes) {
            PlanitFoodEntity entityToFind = new PlanitFoodEntity(EntityType.DISH, dish.getId(), dish.getDishType());
            PlanitFoodEntity foundDish = dynamoDB.getMapper()
                    .load(PlanitFoodEntity.class, entityToFind.getPK(), entityToFind.getSK());
            if (foundDish == null) {
                logger.info("updated dish: " + dish.getName());
                PlanitFoodEntity dishToAdd = new PlanitFoodEntity(EntityType.DISH, dish);
                dynamoDB.getMapper().save(dishToAdd);
            }
            updatedDishes.add(dish);
        }
        logger.info("added all dishes: " + dishes.size());
        return updatedDishes;
    }

    public Dish addDish(Dish dish) throws Exception {
        PlanitFoodEntity planitFoodEntity = new PlanitFoodEntity(EntityType.DISH, dish);
        logger.info("Saving dish id " + dish.getId());
        dynamoDB.getMapper().save(planitFoodEntity);
        logger.info("dish id " + dish.getId() + " saved to db");
        return dish;
    }

    public Dish updateDish(Dish dish) throws Exception {
        logger.info("Update dish id " + dish.getId());
        PlanitFoodEntity toFind =
                new PlanitFoodEntity(EntityType.DISH, dish.getId(), dish.getDishType());
        PlanitFoodEntity found = dynamoDB.getMapper()
                .load(PlanitFoodEntity.class, toFind.getPK(), toFind.getSK());

        if (found != null) {
            dish = addDishIngredientsToDatabase(dish);
            PlanitFoodEntity planitFoodEntity = new PlanitFoodEntity(EntityType.DISH, dish);
            logger.info("Saving dish id " + dish.getId());
            dynamoDB.getMapper().save(planitFoodEntity);
            logger.info("Saved dish id " + dish.getId());
            return dish;
        } else {
            throw new EntityNotFoundException("dish", dish.getId());
        }
    }

    public Dish addDishIngredientsToDatabase(Dish dish) throws Exception {
        List<Ingredient> ingredients = dish.getIngredients();
        dish.setIngredients(ingredientsDataHandler.addIngredientsToDatabase(ingredients));
        return dish;
    }

    public void deleteDish(String id) throws Exception {
        logger.info("Deleting ingredient id " + id);
        List<Meal> found = mealDataHandler.getMealsByQuery(null, id, false);
        if (found != null & found.size() > 0) {
            throw new UnableToDeleteException(id, "dish is used in meal " + found.get(0).getId());
        } else {
            Dish toDelete = getDishById(id);
            this.dynamoDB.getMapper().delete(toDelete);
        }
    }

    public List<Dish> getDishesByQuery(String searchName, DishType dishType, String ingredientId, boolean addIngredients) {
        logger.info("Query dish by " + searchName + ":" + ingredientId + ":" + dishType);
        Map<String, AttributeValue> eav = new HashMap();
        Condition rangeKeyCondition = new Condition();
        List<PlanitFoodEntity> results;

        if (searchName == null && dishType == null) {
            final String ingredientIdQuery = "contains(Ingredients, :val1)";
            eav.put(":val1", new AttributeValue().withS(ingredientId));
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression(ingredientIdQuery)
                    .withExpressionAttributeValues(eav);
            logger.info("Get dishes by ingredient id");
            results = scanDishes(scanExpression);
        } else if (searchName == null) {
            rangeKeyCondition.withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                    .withAttributeValueList(new AttributeValue().withS("DISH_TYPE#" + dishType));
            DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression<PlanitFoodEntity>()
                    .withHashKeyValues(new PlanitFoodEntity(EntityType.DISH))
                    .withRangeKeyCondition("SK", rangeKeyCondition);
            logger.info("Get dishes by Dish Type id");
            results = queryDishes(queryExpression);
        } else {
            HashMap<String, Condition> rangeConditions = new HashMap();
            rangeConditions.put("SEARCH_NAME", new Condition()
                    .withComparisonOperator(ComparisonOperator.CONTAINS)
                    .withAttributeValueList(new AttributeValue().withS(searchName)));

            if (dishType != null) {
                rangeConditions.put("SK", new Condition()
                        .withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                        .withAttributeValueList(new AttributeValue().withS("DISH_TYPE#" + dishType)));
            }

            DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression<PlanitFoodEntity>()
                    .withHashKeyValues(new PlanitFoodEntity(EntityType.DISH))
                    .withRangeKeyConditions(rangeConditions);
            logger.info("Get dishes by search name");
            results = queryDishes(queryExpression);
        }

        if (ingredientId != null && (searchName != null || dishType != null)) {
            logger.info("filter by ingredient id");
            results = results.stream()
                    .filter(r -> r.getQuantities().stream()
                            .filter(q -> q.getIngredientId() == ingredientId).findFirst().isPresent())
                    .collect(Collectors.toList());
        }

        // If a single char then filter the results to just starts with
        if (searchName != null && searchName.length() == 1) {
            logger.info("filter by starts with " + searchName.toLowerCase());
            results = results.stream()
                    .filter(r -> r.getSearchName()
                            .startsWith(searchName.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (addIngredients) {
            logger.info("add ingredients");
            results.stream()
                    .map(r -> addIngredientsToDish(r))
                    .collect(Collectors.toList());
        }

        logger.info("return dishes");
        return results.stream().map(r -> PlanitFoodEntityTypeConverter.convertToDish(r)).collect(Collectors.toList());
    }

    private List<PlanitFoodEntity> scanDishes(DynamoDBScanExpression scanExpression) {
        List<PlanitFoodEntity> matchedDishes = dynamoDB.getMapper().scan(PlanitFoodEntity.class, scanExpression);
        return matchedDishes;
    }

    private List<PlanitFoodEntity> queryDishes(DynamoDBQueryExpression queryExpression) {
        return dynamoDB.getMapper().query(PlanitFoodEntity.class, queryExpression);
    }

    /**
     * TEMP Override until refactor is completed
     *
     * @param dishById
     * @return
     */
    public Dish addIngredientsToDish(Dish dishById) {
        return addIngredientsToDish(new PlanitFoodEntity(EntityType.DISH, dishById));
    }

    public Dish addIngredientsToDish(PlanitFoodEntity dishById) {
        TransactionLoadRequest transactionLoadRequest = new TransactionLoadRequest();
        Dish dishWithIngredients = PlanitFoodEntityTypeConverter.convertToDish(dishById);
        List<Quantity> quantities = dishById.getQuantities();

        if (quantities == null || quantities.size() == 0) {
            return dishWithIngredients;
        }

        quantities.forEach(quantity -> {
                    PlanitFoodEntity load = new PlanitFoodEntity(EntityType.INGREDIENT, quantity.getIngredientId());
                    transactionLoadRequest
                            .addLoad(load);
                }
        );
        List<PlanitFoodEntity> results = Transactions.executeTransactionLoad(transactionLoadRequest, dynamoDB.getMapper());
        dishWithIngredients.setIngredients(results.stream()
                .map(i -> PlanitFoodEntityTypeConverter.convertToIngredientWithQuantity(i, quantities))
                .collect(Collectors.toList()));
        return dishWithIngredients;
    }
}
