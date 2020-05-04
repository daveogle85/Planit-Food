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
@WebMvcTest(DayController.class)
public class DaysTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDayByRange() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/days?startDate=1985-10-08&endDate=1985-10-10"))
                .andDo(print())
                .andExpect(status().isOk());

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();

        Object object = gson.
                fromJson(new FileReader(new File("src\\test\\resources\\mockResponses\\days.json")
                        .getAbsolutePath()), Object.class);
        String expectedResult = gson.toJson(object);

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(expectedResult.equals(contentAsString));
    }

    @Test
    public void shouldReturnDayByDate() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/days?startDate=1985-10-08"))
                .andDo(print())
                .andExpect(status().isOk());

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();

        Object object = gson.
                fromJson(new FileReader(new File("src\\test\\resources\\mockResponses\\day.json")
                        .getAbsolutePath()), Object.class);
        String expectedResult = gson.toJson(object);

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(expectedResult.equals(contentAsString));
    }
}
