package com.planitfood.data;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.planitfood.models.Ingredient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientsDataHandler {

    private final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.EU_WEST_2)
            .build();
    private final DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(this.client);

    public Ingredient getIngredientByName(String name) throws Exception {
        List<Ingredient> itemList;

        final Ingredient gsiKeyObj = new Ingredient();
        gsiKeyObj.setSearchName(name.toLowerCase());
        final DynamoDBQueryExpression<Ingredient> queryExpression =
                new DynamoDBQueryExpression<>();
        queryExpression.setHashKeyValues(gsiKeyObj);
        queryExpression.setIndexName("SearchName-index");
        queryExpression.setConsistentRead(false);   // cannot use consistent read on GSI
        itemList = this.dynamoDBMapper.query(Ingredient.class, queryExpression);
        return itemList.stream()
                .findFirst()
                .orElseThrow(() -> new Exception("No ingredient of that name returned"));
    }

    public void saveIngredient(Ingredient ingredient) {
        dynamoDBMapper.save(ingredient);
    }

    // Must be exact name
    public void deleteIngredient(String name) {
        final Ingredient toDelete = new Ingredient(name);
        this.dynamoDBMapper.delete(toDelete);
    }

    public List<Ingredient> findIngredientsBeginningWith(String searchString) throws Exception {
        Map<String, AttributeValue> eav = new HashMap();
        eav.put(":val1", new AttributeValue().withS(searchString));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(SearchName, :val1)").withExpressionAttributeValues(eav);

        List<Ingredient> matchedIngredients = dynamoDBMapper.scan(Ingredient.class, scanExpression);
        return matchedIngredients;
    }
}
