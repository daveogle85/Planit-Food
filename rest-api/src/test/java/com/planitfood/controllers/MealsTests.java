package com.planitfood.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.planitfood.restApi.PlanitFoodApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.File;
import java.io.FileReader;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = PlanitFoodApplication.class)
@WebMvcTest(MealController.class)
public class MealsTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnAll() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk());

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();

        Object object = gson.
                fromJson(new FileReader(new File("src\\test\\resources\\mockResponses\\meals.json")
                        .getAbsolutePath()), Object.class);
        String expectedResult = gson.toJson(object);

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(expectedResult.equals(contentAsString));
    }

    @Test
    public void shouldReturnMealById() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/meals/meal-1"))
                .andDo(print())
                .andExpect(status().isOk());

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();

        Object object = gson.
                fromJson(new FileReader(new File("src\\test\\resources\\mockResponses\\meal.json")
                        .getAbsolutePath()), Object.class);
        String expectedResult = gson.toJson(object);

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(expectedResult.equals(contentAsString));
    }
}
