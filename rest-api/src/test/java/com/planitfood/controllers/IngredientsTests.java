package com.planitfood.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.planitfood.data.IngredientsDataHandler;
import com.planitfood.models.Ingredient;
import com.planitfood.restApi.PlanitFoodApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.File;
import java.io.FileReader;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = PlanitFoodApplication.class)
@WebMvcTest(IngredientController.class)
public class IngredientsTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IngredientsDataHandler ingredientsDataHandler;

//    @Test
//    public void shouldReturnAll() throws Exception {
//        ResultActions resultActions = this.mockMvc.perform(get("/ingredients"))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        Gson gson = new Gson();
//        Object object = gson.
//                fromJson(new FileReader(new File("src\\test\\resources\\mockResponses\\ingredients.json")
//                        .getAbsolutePath()), Object.class);
//        String expectedResult = gson.toJson(object);
//
//        MvcResult result = resultActions.andReturn();
//        String contentAsString = result.getResponse().getContentAsString();
//        Assertions.assertTrue(expectedResult.equals(contentAsString));
//    }

    @Test
    public void shouldReturnNamedIngredient() throws Exception {
        Ingredient test = new Ingredient("Melon");
        given(ingredientsDataHandler.getIngredientByName(eq("melon"))).willReturn(test);
        ResultActions resultActions = this.mockMvc.perform(get("/ingredients/melon"))
                .andDo(print())
                .andExpect(status().isOk());

        Gson gson = new Gson();
        Object object = gson.
                fromJson(new FileReader(new File("src\\test\\resources\\mockResponses\\ingredient.json")
                        .getAbsolutePath()), Object.class);
        String expectedResult = gson.toJson(object);

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(expectedResult.equals(contentAsString));
    }

    @Test
    public void shouldAddANewIngredient() throws Exception {
        Ingredient test = new Ingredient("Melon");
        String url = "/ingredients";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(test);

        mockMvc.perform(post(url).contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }
}