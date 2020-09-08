package com.planitfood.typeConverters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.planitfood.models.Quantity;

import java.util.ArrayList;
import java.util.List;

public class QuantitiesTypeConverter implements DynamoDBTypeConverter<List<String>, List<Quantity>> {
    @Override
    public List<String> convert(List<Quantity> quantities) {
        Gson gson = new Gson();
        ArrayList<String> converted = new ArrayList<>();
        for (Quantity quantity : quantities) {
            converted.add(gson.toJson(quantity));
        }
        return converted;
    }

    @Override
    public List<Quantity> unconvert(List<String> dbValues) {
        Gson gson = new Gson();
        List<Quantity> results = new ArrayList<>();

        for (String quantity : dbValues) {
            results.add(gson.fromJson(quantity, Quantity.class));
        }
        return results;
    }
}
