package com.planitfood.data;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.planitfood.exceptions.EntityNotFoundException;
import com.planitfood.models.Day;
import com.planitfood.models.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MONTHS;

@Service
public class DayDataHandler {
    @Autowired
    private MealDataHandler mealDataHandler;
    private final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.EU_WEST_2)
            .build();
    private final DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(this.client);

    public List<Day> getDayByRange(final LocalDate startDate, LocalDate optionalEndDate) throws Exception {
        List<Day> results = new ArrayList<>();
        if (optionalEndDate == null) {
            optionalEndDate = startDate;
        }

        final LocalDate endDate = optionalEndDate;
        List<String> dayIds = getDayIdsForRange(startDate, endDate);

        dayIds.forEach(id -> {
            results.addAll(queryByIdAndRange(id, startDate, endDate));
        });
        return results;
    }

    public List<Day> getDaysByQuery(String mealId, boolean withMeals) {
        Map<String, AttributeValue> eav = new HashMap();
        final String mealIdQuery = "MealID = :val1";
        eav.put(":val1", new AttributeValue().withS(mealId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression(mealIdQuery)
                .withExpressionAttributeValues(eav);

        List<Day> results = scanDays(scanExpression);
        if (!withMeals) {
            return results;
        }
        return results.stream().map(r -> addMealToDay(r)).collect(Collectors.toList());
    }

    public void addDay(Day day) throws Exception {
        dynamoDBMapper.save(day);
    }

    public void updateDay(Day day) throws Exception {
        Day found = dynamoDBMapper.load(Day.class, day.getId(), day.getDate());

        if (found != null) {
            dynamoDBMapper.save(day);
        } else {
            throw new EntityNotFoundException("day", day.getDate().toString());
        }
    }

    public void deleteDay(LocalDate date) {
        final Day toDelete = new Day(date);
        this.dynamoDBMapper.delete(toDelete);
    }

    private List<Day> scanDays(DynamoDBScanExpression scanExpression) {
        List<Day> matchedDishes = dynamoDBMapper.scan(Day.class, scanExpression);
        return matchedDishes;
    }

    private List<Day> queryByIdAndRange(String id, LocalDate startDate, LocalDate endDate) {
        Map<String, AttributeValue> eav = new HashMap();
        final String rangeQuery = "ID = :val1 and DayDate between :val2 and :val3";
        eav.put(":val1", new AttributeValue().withS(id));
        eav.put(":val2", new AttributeValue().withS(startDate.toString()));
        eav.put(":val3", new AttributeValue().withS(endDate.toString()));
        final String rangeQuery1 = "ID = :val1 and DayDate between :val2 and :val3";

        DynamoDBQueryExpression<Day> queryExpression = new DynamoDBQueryExpression<Day>()
                .withKeyConditionExpression(rangeQuery)
                .withKeyConditionExpression(rangeQuery1)
                .withExpressionAttributeValues(eav);

        List<Day> results = dynamoDBMapper.query(Day.class, queryExpression);
        return results.stream().map(r -> addMealToDay(r)).collect(Collectors.toList());

    }

    public Day addMealToDay(Day day) {
        Meal dayMeal = day.getMeal();

        if (dayMeal == null) {
            return day;
        }

        dayMeal = dynamoDBMapper.load(Meal.class, dayMeal.getId());
        dayMeal = mealDataHandler.addDishIdAndNameToMeal(dayMeal);
        day.setMeal(dayMeal);
        return day;
    }

    private List<String> getDayIdsForRange(LocalDate startDate, LocalDate endDate) {
        long monthsBetween = getMonthsBetween(startDate, endDate);
        List<String> ids = new ArrayList<>();
        ids.add(Day.createId(startDate));
        for (int i = 0; i < monthsBetween; i++) {
            startDate = startDate.plusMonths(1);
            ids.add(Day.createId(startDate));
        }
        return ids;
    }

    private long getMonthsBetween(LocalDate startDate, LocalDate endDate) {
        return MONTHS.between(
                YearMonth.from(startDate),
                YearMonth.from(endDate)
        );
    }

    private List<Meal> scanMeals(DynamoDBScanExpression scanExpression) {
        List<Meal> matchedDishes = dynamoDBMapper.scan(Meal.class, scanExpression);
        return matchedDishes;
    }

//    private Meal addDishToMeal(Meal meal) {
//        TransactionLoadRequest transactionLoadRequest = new TransactionLoadRequest();
//        List<Dish> mealDishes = meal.getDishes();
//
//        if (mealDishes == null) {
//            return meal;
//        }
//
//        mealDishes.forEach(dish -> transactionLoadRequest.addLoad(dish));
//        List<Dish> results = Transactions.executeTransactionLoad(transactionLoadRequest, dynamoDBMapper);
//        results = results.stream().map(dish -> dishDataHandler.addIngredientsToDish(dish)).collect(Collectors.toList());
//        meal.setDishes(results);
//        return meal;
//    }
}
