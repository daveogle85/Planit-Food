package com.planitfood.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.planitfood.enums.EntityType;
import com.planitfood.exceptions.EntityNotFoundException;
import com.planitfood.exceptions.UnableToDeleteException;
import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import com.planitfood.models.PlanitFoodEntity;
import com.planitfood.typeConverters.PlanitFoodEntityTypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IngredientsDataHandler {

    private static Logger logger = LogManager.getLogger(IngredientsDataHandler.class);

    @Autowired
    private DishDataHandler dishDataHandler;

    @Autowired
    DynamoDB dynamoDB;

    public List<Ingredient> getAllIngredients() {
        logger.info("Get all ingredients");
        PlanitFoodEntity planitFoodEntity = new PlanitFoodEntity(EntityType.INGREDIENT);
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(planitFoodEntity);

        List<PlanitFoodEntity> results = dynamoDB.getMapper().query(PlanitFoodEntity.class, queryExpression);
        logger.info("return all ingredients: " + results.size());
        return results.stream().map((result) -> PlanitFoodEntityTypeConverter.convertToIngredient(result)).collect(Collectors.toList());
    }

    public Ingredient getIngredientById(String id) throws Exception {
        logger.info("Get ingredient id " + id);
        PlanitFoodEntity planitFoodEntity = new PlanitFoodEntity(EntityType.INGREDIENT, id);
        PlanitFoodEntity found = dynamoDB.getMapper()
                .load(PlanitFoodEntity.class, planitFoodEntity.getPK(), planitFoodEntity.getSK());

        if (found != null) {
            logger.info("Return ingredient " + found.getName());
            return PlanitFoodEntityTypeConverter.convertToIngredient(found);
        } else {
            throw new EntityNotFoundException("ingredient", id);
        }
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        PlanitFoodEntity planitFoodEntity = new PlanitFoodEntity(EntityType.INGREDIENT, ingredient);
        logger.info("Saving ingredient id " + ingredient.getId());
        dynamoDB.getMapper().save(planitFoodEntity);
        logger.info("ingredient id " + ingredient.getId() + " saved to db");
        return ingredient;
    }

    public List<Ingredient> addIngredientsToDatabase(List<Ingredient> ingredients) throws Exception {
        logger.info("add all ingredients: " + ingredients.size());
        List<Ingredient> updatedIngredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            PlanitFoodEntity planitFoodEntity = new PlanitFoodEntity(EntityType.INGREDIENT, ingredient);
            PlanitFoodEntity foundIngredient = ingredient.getId() == null ?
                    null :
                    dynamoDB.getMapper().load(PlanitFoodEntity.class, planitFoodEntity.getPK(), planitFoodEntity.getSK());
            if (foundIngredient != null) {
                logger.info("ingredient id " + ingredient.getId() + " already in db");
                updatedIngredients.add(addIngredient(ingredient));
            } else {
                logger.info("Saving ingredient id " + ingredient.getId());
                dynamoDB.getMapper().save(planitFoodEntity);
                updatedIngredients.add(ingredient);
            }
        }
        logger.info("add all ingredients complete");
        return updatedIngredients;
    }

    public Ingredient updateIngredient(Ingredient ingredient) throws Exception {
        logger.info("Update ingredient id " + ingredient.getId());
        PlanitFoodEntity planitFoodEntity = new PlanitFoodEntity(EntityType.INGREDIENT, ingredient);
        PlanitFoodEntity found = dynamoDB.getMapper()
                .load(PlanitFoodEntity.class, planitFoodEntity.getPK(), planitFoodEntity.getSK());

        if (found != null) {
            logger.info("Saving ingredient id " + ingredient.getId());
            dynamoDB.getMapper().save(planitFoodEntity);
            logger.info("Saved ingredient id " + ingredient.getId());
            return ingredient;
        } else {
            throw new EntityNotFoundException("ingredient", ingredient.getId());
        }
    }

    public void deleteIngredient(String id) throws UnableToDeleteException {
        logger.info("Deleting ingredient id " + id);
        List<Dish> found = dishDataHandler.getDishesByQuery(null, null, id, false);
        if (found != null & found.size() > 0) {
            throw new UnableToDeleteException(id, "ingredient is used in dish " + found.get(0).getId());
        } else {
            PlanitFoodEntity planitFoodEntity = new PlanitFoodEntity(EntityType.INGREDIENT, id);
            this.dynamoDB.getMapper().delete(planitFoodEntity);
            logger.info("Deleted ingredient id " + id);
        }
    }

    public List<Ingredient> findIngredientsBeginningWith(String searchString) {
        logger.info("Search for ingredient containing " + searchString);
        Condition rangeKeyCondition = new Condition();
        rangeKeyCondition.withComparisonOperator(ComparisonOperator.CONTAINS)
                .withAttributeValueList(new AttributeValue().withS(searchString));
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression<PlanitFoodEntity>()
                .withHashKeyValues(new PlanitFoodEntity(EntityType.INGREDIENT))
                .withRangeKeyCondition("SEARCH_NAME", rangeKeyCondition);
        List<PlanitFoodEntity> matchedIngredients = dynamoDB.getMapper().query(PlanitFoodEntity.class, queryExpression);
        logger.info("Found " + matchedIngredients.size() + " ingredients containing " + searchString);
        return matchedIngredients.stream()
                .map(i -> PlanitFoodEntityTypeConverter.convertToIngredient(i))
                .collect(Collectors.toList());
    }
}
