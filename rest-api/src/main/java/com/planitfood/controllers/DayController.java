package com.planitfood.controllers;

import com.planitfood.data.DayDataHandler;
import com.planitfood.models.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@ComponentScan({"com.planitfood.data"})
public class DayController {

    @Autowired
    DayDataHandler dayDataHandler;

    @GetMapping("/days")
    List<Day> search(
            @RequestParam(required = false) Boolean includeDishes,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) throws Exception {
        try {
            boolean withDishes = includeDishes != null && includeDishes;
            return dayDataHandler.getDayByRange(startDate, endDate, withDishes);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/days")
    Day newDay(@RequestBody Day newDay) throws Exception {
        try {
            newDay = dayDataHandler.addDay(newDay);
            return newDay;
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/days")
    Day replaceDay(@RequestBody Day newDay) throws Exception {
        try {
            newDay = dayDataHandler.updateDay(newDay);
            return newDay;
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/days/{date}")
    String deleteDay(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws Exception {
        try {
            dayDataHandler.deleteDay(date);
            return date.toString() + " deleted";
        } catch (Exception e) {
            throw e;
        }
    }
}
