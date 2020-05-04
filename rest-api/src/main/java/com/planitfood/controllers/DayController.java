package com.planitfood.controllers;

import com.planitfood.data.StaticData;
import com.planitfood.models.Day;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class DayController {

    @GetMapping("/days")
    List<Day> one(@RequestParam("startDate")
                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                  @RequestParam(value = "endDate", required = false)
                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return StaticData.getDays(startDate, endDate);
    }

    @PostMapping("/days")
    Day newDay(@RequestBody Day newDay) {
        System.out.println("new Day added");
        return newDay;
    }

    @PutMapping("/days/{id}")
    Day replaceDay(@RequestBody Day newDay, @PathVariable String id) {
        System.out.println("Did a put with " + id);
        return null; // TODO
    }

    @DeleteMapping("/days/{id}")
    void deleteDay(@PathVariable String id) {
        System.out.println("Deleted " + id);
    }
}
