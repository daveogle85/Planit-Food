package com.planitfood.typeConverters;

import com.planitfood.models.Ingredient;
import com.planitfood.models.PlanitFoodEntity;

public class PlanitFoodEntityTypeConverter {

    public static Ingredient convertToIngredient(PlanitFoodEntity entity) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(entity.getId());
        ingredient.setName(entity.getName());
        ingredient.setQuantity(entity.getQuantities().getQuantity());
        ingredient.setUnit(entity.getQuantities().getUnit());
        return ingredient;
    }
}
