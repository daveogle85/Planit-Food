package com.planitfood.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.planitfood.exceptions.EntityNotFoundException;
import com.planitfood.exceptions.UnableToDeleteException;
import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IngredientsDataHandler {

    @Autowired
    private DishDataHandler dishDataHandler;

    @Autowired
    DynamoDB dynamoDB;

    public List<Ingredient> getAllIngredients() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Ingredient> results = dynamoDB.getMapper().scan(Ingredient.class, scanExpression);
        return results;
    }

    public Ingredient getIngredientById(String id) throws Exception {
        Ingredient found = dynamoDB.getMapper().load(Ingredient.class, id);

        if (found != null) {
            return found;
        } else {
            throw new EntityNotFoundException("ingredient", id);
        }
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        dynamoDB.getMapper().save(ingredient);
        return ingredient;
    }

    public List<Ingredient> addIngredientsToDatabase(List<Ingredient> ingredients) throws Exception {
        List<Ingredient> updatedIngredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            Ingredient foundIngredient = ingredient.getId() == null ?
                    null :
                    dynamoDB.getMapper().load(Ingredient.class, ingredient.getId());
            if (foundIngredient == null) {
                updatedIngredients.add(addIngredient(ingredient));
            } else {
                dynamoDB.getMapper().save(ingredient);
                updatedIngredients.add(ingredient);
            }
        }
        return updatedIngredients;
    }

    public Ingredient updateIngredient(Ingredient ingredient) throws Exception {
        Ingredient found = dynamoDB.getMapper().load(Ingredient.class, ingredient.getId());

        if (found != null) {
            dynamoDB.getMapper().save(ingredient);
            return ingredient;
        } else {
            throw new EntityNotFoundException("ingredient", ingredient.getId());
        }
    }

    public void deleteIngredient(String id) throws UnableToDeleteException {
        final Ingredient toDelete = new Ingredient(id);
        List<Dish> found = dishDataHandler.getDishesByQuery(null, id, null, false);
        if (found != null & found.size() > 0) {
            throw new UnableToDeleteException(id, "ingredient is used in dish " + found.get(0).getId());
        } else {
            this.dynamoDB.getMapper().delete(toDelete);
        }
    }

    public List<Ingredient> findIngredientsBeginningWith(String searchString) {
        Map<String, AttributeValue> eav = new HashMap();
        eav.put(":val1", new AttributeValue().withS(searchString));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(SearchName, :val1)").withExpressionAttributeValues(eav);

        List<Ingredient> matchedIngredients = dynamoDB.getMapper().scan(Ingredient.class, scanExpression);
        return matchedIngredients;
    }
}
