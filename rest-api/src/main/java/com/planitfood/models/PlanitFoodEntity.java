package com.planitfood.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.planitfood.enums.DishType;
import com.planitfood.enums.EntityType;
import com.planitfood.typeConverters.DishTypeTypeConverter;
import java.util.List;
import java.util.UUID;

/**
 * Class to model the Entity table in DynamoDB.
 * This table holds details on Meals, Dishes, Lists & Ingredients.
 */
@DynamoDBTable(tableName = "Entity")
public class PlanitFoodEntity {

    private String PK;
    private String SK;
    private String id;
    private String name;
    private String searchName;
    private List<String> dishIds;
    private String notes;
    private DishType dishType;
    private Float cookingTime;
    private Quantity quantities;

    public PlanitFoodEntity() {
    }

    public PlanitFoodEntity(EntityType type) {
        this.PK = generatePK(type);
    }

    public PlanitFoodEntity(EntityType type, String ingredientId) {
        this.PK = generatePK(type);
        this.SK = generateIngredientHashKey(ingredientId);
    }

    public PlanitFoodEntity(EntityType type, Ingredient ingredient) {

        String ingredientId = ingredient.getId();
        if (ingredientId == null) {
            ingredientId = UUID.randomUUID().toString();
        }

        this.PK = generatePK(type);
        this.SK = generateIngredientHashKey(ingredientId);
        this.id = ingredientId;
        this.name = ingredient.getName();
        this.searchName = ingredient.getSearchName();
        this.dishIds = null;
        this.notes = null;
        this.dishType = null;
        this.cookingTime = null;
        this.quantities = new Quantity(ingredient.getQuantity(), ingredient.getUnit(), ingredient.getId());
    }

    @DynamoDBHashKey(attributeName = "PK")
    public String getPK() {
        return PK;
    }
    public void setPK(String PK) {
        this.PK = PK;
    }

    @DynamoDBRangeKey(attributeName = "SK")
    public String getSK() {
        return SK;
    }
    public void setSK(String SK) {
        this.SK = SK;
    }

    @DynamoDBAttribute(attributeName = "ID")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "NAME")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBIndexHashKey(attributeName = "SEARCH_NAME", globalSecondaryIndexName = "SearchName-index")
    public String getSearchName() {
        return searchName;
    }
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    @DynamoDBAttribute(attributeName = "DISH_IDS")
    public List<String> getDishIds() {
        return dishIds;
    }
    public void setDishIds(List<String> dishIds) {
        this.dishIds = dishIds;
    }

    @DynamoDBAttribute(attributeName = "NOTES")
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @DynamoDBAttribute(attributeName = "DISH_TYPE")
    @DynamoDBTypeConverted(converter = DishTypeTypeConverter.class)
    public DishType getDishType() {
        return dishType;
    }
    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    @DynamoDBAttribute(attributeName = "COOKING_TIME")
    public Float getCookingTime() {
        return cookingTime;
    }
    public void setCookingTime(Float cookingTime) {
        this.cookingTime = cookingTime;
    }

    @DynamoDBAttribute(attributeName = "QUANTITIES")
    @DynamoDBTypeConvertedJson
    public Quantity getQuantities() {
        return quantities;
    }
    public void setQuantities(Quantity quantities) {
        this.quantities = quantities;
    }

    private String generatePK(EntityType entityType) {
        return "ENTITY_TYPE#" + entityType.toString();
    }

    private String generateIngredientHashKey(String id) {
        return "ID#" + id;
    }
}
