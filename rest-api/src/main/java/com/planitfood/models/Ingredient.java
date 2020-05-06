package com.planitfood.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.planitfood.enums.Unit;
import com.planitfood.typeConverters.UnitTypeConverter;
import lombok.Data;

@Data
@DynamoDBTable(tableName="Ingredient")
public class Ingredient {

    private String name;
    private String searchName;
    private float quantity = 0.0f;
    private Unit unit = Unit.UNIT;

    public Ingredient() {};

    public Ingredient(String name) {
        this.name = name;
        this.searchName = name.toLowerCase();
    }

    @DynamoDBHashKey(attributeName = "Name")
    public String getName() {
        return name;
    }

    @DynamoDBHashKey(attributeName = "Name")
    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBIndexHashKey(attributeName = "SearchName", globalSecondaryIndexName = "SearchName-index")
    @DynamoDBRangeKey(attributeName="SearchName")
    public String getSearchName() {
        return searchName;
    }

    @DynamoDBIndexHashKey(attributeName = "SearchName", globalSecondaryIndexName = "SearchName-index")
    @DynamoDBRangeKey(attributeName="SearchName")
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    @DynamoDBTypeConverted(converter = UnitTypeConverter.class)
    @DynamoDBAttribute(attributeName = "Unit")
    public Unit getUnit() {
        return unit;
    }

    @DynamoDBAttribute(attributeName = "Unit")
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
