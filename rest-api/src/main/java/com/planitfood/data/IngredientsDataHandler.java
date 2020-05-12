package com.planitfood.data;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.planitfood.exceptions.EntityNotFoundException;
import com.planitfood.exceptions.UnableToDeleteException;
import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientsDataHandler {

    @Autowired
    private DishDataHandler dishDataHandler;
    private final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.EU_WEST_2)
            .build();
    private final DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(this.client);

    public List<Ingredient> getAllIngredients() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Ingredient> results = dynamoDBMapper.scan(Ingredient.class, scanExpression);
        return results;
    }

    public Ingredient getIngredientById(String id) throws Exception {
        Ingredient found = dynamoDBMapper.load(Ingredient.class, id);

        if (found != null) {
            return found;
        } else {
            throw new EntityNotFoundException("ingredient", id);
        }

//        List<Ingredient> itemList;
//
//        final Ingredient gsiKeyObj = new Ingredient();
//        gsiKeyObj.setSearchName(name.toLowerCase());
//        final DynamoDBQueryExpression<Ingredient> queryExpression =
//                new DynamoDBQueryExpression<>();
//        queryExpression.setHashKeyValues(gsiKeyObj);
//        queryExpression.setIndexName("SearchName-index");
//        queryExpression.setConsistentRead(false);   // cannot use consistent read on GSI
//        itemList = this.dynamoDBMapper.query(Ingredient.class, queryExpression);
//        return itemList.stream()
//                .findFirst()
//                .orElseThrow(() -> new Exception("No ingredient of that name returned"));
    }

    public void addIngredient(Ingredient ingredient) {
        dynamoDBMapper.save(ingredient);
    }

    public void updateIngredient(Ingredient ingredient) throws Exception {
        Ingredient found = dynamoDBMapper.load(Ingredient.class, ingredient.getId());

        if (found != null) {
            dynamoDBMapper.save(ingredient);
        } else {
            throw new EntityNotFoundException("ingredient", ingredient.getId());
        }
    }

    public void deleteIngredient(String id) throws UnableToDeleteException {
        final Ingredient toDelete = new Ingredient(id);
        List<Dish> found = dishDataHandler.getDishesByQuery(null, id, null);
        if(found != null & found.size() > 0) {
            throw new UnableToDeleteException(id, "ingredient is used in dish " + found.get(0).getId());
        } else {
            this.dynamoDBMapper.delete(toDelete);
        }
    }

    public List<Ingredient> findIngredientsBeginningWith(String searchString) {
        Map<String, AttributeValue> eav = new HashMap();
        eav.put(":val1", new AttributeValue().withS(searchString));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(SearchName, :val1)").withExpressionAttributeValues(eav);

        List<Ingredient> matchedIngredients = dynamoDBMapper.scan(Ingredient.class, scanExpression);
        return matchedIngredients;
    }
}
