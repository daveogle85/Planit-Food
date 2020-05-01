package com.planitfood.controllers;

import com.google.gson.Gson;
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
@WebMvcTest(IngredientController.class)
public class IngredientsTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnAll() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/ingredients"))
                .andDo(print())
                .andExpect(status().isOk());

        Gson gson = new Gson();
        Object object = gson.
                fromJson(new FileReader(new File("src\\test\\resources\\mockResponses\\ingredients.json")
                        .getAbsolutePath()), Object.class);
        String expectedResult = gson.toJson(object);

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(expectedResult.equals(contentAsString));
    }
}