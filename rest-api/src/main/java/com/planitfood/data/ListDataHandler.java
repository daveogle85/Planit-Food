package com.planitfood.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.planitfood.exceptions.EntityNotFoundException;
import com.planitfood.exceptions.UnableToDeleteException;
import com.planitfood.models.CustomList;
import com.planitfood.models.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ListDataHandler {

    @Autowired
    private DynamoDB dynamoDB;

    @Autowired
    private MealDataHandler mealDataHandler;

    public CustomList getListById(String id) throws Exception {
        CustomList found = dynamoDB.getMapper().load(CustomList.class, id);

        if (found != null) {
            return addMealsToList(found);
        } else {
            throw new EntityNotFoundException("list", id);
        }
    }

    public CustomList getListByName(String name, boolean addMeals) throws Exception {
        Map<String, AttributeValue> eav = new HashMap();
        final String nameQuery = "ListName = :val1";
        eav.put(":val1", new AttributeValue().withS(name));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(nameQuery)
                .withExpressionAttributeValues(eav);
        List<CustomList> results = scanLists(scanExpression);
        CustomList firstResult = (results != null && results.size() > 0) ? results.get(0) : null;

        if (!addMeals) {
            return firstResult;
        }
        return addMealsToList(firstResult);
    }

    /**
     * Do not return meals for all lists request
     * only meal ids.
     * @return
     */
    public List<CustomList> getAllLists() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<CustomList> results = dynamoDB.getMapper().scan(CustomList.class, scanExpression);
//        return results.stream().map(r -> addMealsToList(r)).collect(Collectors.toList());
        return results;
    }

    public CustomList addList(CustomList customList) throws Exception {
        dynamoDB.getMapper().save(customList);
        return customList;
    }

    public CustomList updateMealsInList(String listId, Meal newMeal) throws Exception {
        CustomList found = dynamoDB.getMapper().load(CustomList.class, listId);
        if (found != null) {
            newMeal = mealDataHandler.addDishesToDatabase(newMeal);
            found.addMeal(newMeal);
            dynamoDB.getMapper().save(found);
        } else {
            throw new EntityNotFoundException("list", listId);
        }
        return found;
    }

    public void deleteList(String id) throws UnableToDeleteException {
        final CustomList toDelete = new CustomList(id);
        CustomList found = dynamoDB.getMapper().load(CustomList.class, id);
        if (found == null) {
            throw new UnableToDeleteException(id, "list does not exist: " + id);
        }
        else if (found.getName().toLowerCase().equals("default")) {
            throw new UnableToDeleteException(id, "cannot delete default list " + id);
        } else {
            this.dynamoDB.getMapper().delete(toDelete);
        }
    }

    public CustomList addMealsToList(CustomList mealList) {
        List<Meal> meals = mealList.getMeals();
        List<Meal> updatedMeals = new ArrayList<>();

        if (meals == null || meals.size() == 0) {
            return mealList;
        }

        for (Meal meal : meals) {
            Meal mealToAdd = dynamoDB.getMapper().load(Meal.class, meal.getId());
            mealToAdd = mealDataHandler.addDishIdAndNameToMeal(mealToAdd);
            updatedMeals.add(mealToAdd);
        }

        mealList.setMeals(updatedMeals);

        return mealList;
    }

    private List<CustomList> scanLists(DynamoDBScanExpression scanExpression) {
        List<CustomList> matchedLists = dynamoDB.getMapper().scan(CustomList.class, scanExpression);
        return matchedLists;
    }
}
