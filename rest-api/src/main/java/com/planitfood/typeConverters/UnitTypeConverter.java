package com.planitfood.typeConverters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.planitfood.enums.Unit;

public class UnitTypeConverter implements DynamoDBTypeConverter<String, Unit> {
    @Override
    public String convert(Unit unit) {
        return unit.toString();
    }

    @Override
    public Unit unconvert(String s) {
        return Unit.valueOf(s);
    }
}
