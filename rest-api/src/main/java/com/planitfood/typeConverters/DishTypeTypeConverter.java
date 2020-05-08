package com.planitfood.typeConverters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.planitfood.enums.DishType;

public class DishTypeTypeConverter implements DynamoDBTypeConverter<String, DishType> {
    @Override
    public String convert(DishType dishType) {
        return dishType.toString();
    }

    @Override
    public DishType unconvert(String s) {
        return DishType.valueOf(s);
    }
}
