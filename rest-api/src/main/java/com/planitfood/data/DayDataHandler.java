package com.planitfood.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionLoadRequest;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.planitfood.controllers.DayController;
import com.planitfood.exceptions.EntityNotFoundException;
import com.planitfood.models.Day;
import com.planitfood.models.Meal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static Logger logger = LogManager.getLogger(DayDataHandler.class);

    @Autowired
    private DynamoDB dynamoDB;

    public List<Day> getDayByRange(final LocalDate startDate, LocalDate optionalEndDate, boolean withDishes) throws Exception {
        if (optionalEndDate == null) {
            optionalEndDate = startDate;
        }

        final LocalDate endDate = optionalEndDate;
        List<String> dayIds = getDayIdsForRange(startDate, endDate);

        return queryByIdAndRange(dayIds, startDate, endDate, withDishes);
    }

    public List<Day> getDaysByQuery(String mealId, boolean withMeals) {
        Map<String, AttributeValue> eav = new HashMap();
        final String mealIdQuery = "MealID = :val1";
        eav.put(":val1", new AttributeValue().withS(mealId));

        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withFilterExpression(mealIdQuery)
                .withExpressionAttributeValues(eav);

        return queryDays(queryExpression);
    }

    public Day addDay(Day day) throws Exception {
        dynamoDB.getMapper().save(day);
        return day;
    }

    public Day updateDay(Day day) throws Exception {
        Day found = dynamoDB.getMapper().load(Day.class, day.getId(), day.getDate());

        if (found != null) {
            dynamoDB.getMapper().save(day);
            return day;
        } else {
            throw new EntityNotFoundException("day", day.getDate().toString());
        }
    }

    public Day deleteDay(LocalDate date) {
        final Day toDelete = new Day(date);
        this.dynamoDB.getMapper().delete(toDelete);
        return toDelete;
    }

    private List<Day> scanDays(DynamoDBScanExpression scanExpression) {
        List<Day> matchedDishes = dynamoDB.getMapper().scan(Day.class, scanExpression);
        return matchedDishes;
    }

    private List<Day> queryDays(DynamoDBQueryExpression queryExpression) {
        List<Day> matchedDishes = dynamoDB.getMapper().query(Day.class, queryExpression);
        return matchedDishes;
    }

    private List<Day> queryByIdAndRange(List<String> ids, LocalDate startDate, LocalDate endDate, boolean withDishes) {
        logger.info("start queryByIdAndRange");

        if (ids.size() == 0) {
            return  new ArrayList<>();
        }

        ArrayList<Day> results = new ArrayList<>();

        for(int i = 0; i < ids.size(); i++) {
            Map<String, AttributeValue> eav = new HashMap();
            eav.put(":startDate", new AttributeValue().withS(startDate.toString()));
            eav.put(":endDate", new AttributeValue().withS(endDate.toString()));
            eav.put(":val1", new AttributeValue().withS(ids.get(i)));

            DynamoDBQueryExpression<Day> queryExpression = new DynamoDBQueryExpression<Day>()
                    .withKeyConditionExpression("ID = :val1 and DayDate between :startDate and :endDate")
                    .withExpressionAttributeValues(eav);

            results.addAll(dynamoDB.getMapper().query(Day.class, queryExpression));
        }

        logger.info("return queryByIdAndRange " + ids);
        return results;

    }

    private List<String> getDayIdsForRange(LocalDate startDate, LocalDate endDate) {
        logger.info("get id's for range...");
        long monthsBetween = getMonthsBetween(startDate, endDate);
        List<String> ids = new ArrayList<>();
        ids.add(Day.createId(startDate));
        for (int i = 0; i < monthsBetween; i++) {
            startDate = startDate.plusMonths(1);
            ids.add(Day.createId(startDate));
        }
        logger.info("return id's for range...");
        return ids;
    }

    private long getMonthsBetween(LocalDate startDate, LocalDate endDate) {
        return MONTHS.between(
                YearMonth.from(startDate),
                YearMonth.from(endDate)
        );
    }

    private List<Meal> scanMeals(DynamoDBScanExpression scanExpression) {
        List<Meal> matchedDishes = dynamoDB.getMapper().scan(Meal.class, scanExpression);
        return matchedDishes;
    }
}
