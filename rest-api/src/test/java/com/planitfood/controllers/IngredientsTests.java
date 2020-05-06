package com.planitfood.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.planitfood.data.IngredientsDataHandler;
import com.planitfood.models.Ingredient;
import com.planitfood.restApi.PlanitFoodApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = PlanitFoodApplication.class)
@WebMvcTest(IngredientController.class)
public class IngredientsTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IngredientsDataHandler ingredientsDataHandler;

    @Test
    public void shouldSearchForIngredient() throws Exception {
        String url = "/ingredients?searchString=mel";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(ingredientsDataHandler, times(1)).findIngredientsBeginningWith("mel");
    }

    @Test
    public void shouldReturnNamedIngredient() throws Exception {
        String url = "/ingredients/melon";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(ingredientsDataHandler, times(1)).getIngredientByName("melon");
    }

    @Test
    public void shouldAddANewIngredient() throws Exception {
        Ingredient test = new Ingredient("Melon");
        String url = "/ingredients";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(test);

        mockMvc.perform(post(url).contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        verify(ingredientsDataHandler, times(1)).saveIngredient(test);
    }

    @Test
    public void shouldUpdateIngredient() throws Exception {
        Ingredient update = new Ingredient("Melon");
        String url = "/ingredients/melon";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(update);

        mockMvc.perform(put(url).contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        verify(ingredientsDataHandler, times(1)).saveIngredient(update);
    }

    @Test
    public void shouldDeleteIngredient() throws Exception {
        mockMvc.perform(delete("/ingredients/Melon"))
                .andExpect(status().isOk());
        verify(ingredientsDataHandler, times(1)).deleteIngredient("Melon");
    }
}