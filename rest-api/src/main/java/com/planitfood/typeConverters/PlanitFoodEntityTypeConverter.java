package com.planitfood.typeConverters;

import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import com.planitfood.models.PlanitFoodEntity;
import com.planitfood.models.Quantity;

import java.util.List;
import java.util.stream.Collectors;

public class PlanitFoodEntityTypeConverter {

    public static Ingredient convertToIngredient(PlanitFoodEntity entity) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(entity.getId());
        ingredient.setName(entity.getName());
        return ingredient;
    }

    public static Ingredient convertToIngredientWithQuantity(PlanitFoodEntity entity, List<Quantity> quantities) {
        Ingredient ingredient = convertToIngredient(entity);
        Quantity quantity = quantities.stream().filter(q -> q.getIngredientId() == entity.getId())
                .findFirst().orElse(null);

        if (quantity != null) {
            ingredient.setQuantity(quantity.getQuantity());
            ingredient.setUnit(quantity.getUnit());
        }
        return ingredient;
    }

    public static List<Quantity> convertIngredientsListToQuantities(List<Ingredient> ingredients) {
        return ingredients.stream().map(i -> {
            return new Quantity(i.getQuantity(), i.getUnit(), i.getId());
        }).collect(Collectors.toList());
    }

    public static Dish convertToDish(PlanitFoodEntity entity) {
        Dish dish = new Dish();
        dish.setId(entity.getId());
        dish.setName(entity.getName());
        dish.setDishType(entity.getDishType());
        dish.setNotes(entity.getNotes());
        dish.setCookingTime(entity.getCookingTime());
        dish.setQuantities(entity.getQuantities());
        return dish;
    }
}
