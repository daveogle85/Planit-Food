package com.planitfood.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.planitfood.data.IngredientsDataHandler;
import com.planitfood.models.Ingredient;
import com.planitfood.restApi.PlanitFoodApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .build();
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldReturnAllIngredients() throws Exception {
        String url = "/ingredients";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(ingredientsDataHandler, times(1)).getAllIngredients();
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldSearchForIngredient() throws Exception {
        String url = "/ingredients?searchString=mel";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(ingredientsDataHandler, times(1))
                .findIngredientsBeginningWith("mel");
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldReturnIngredientById() throws Exception {
        String url = "/ingredients/123";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(ingredientsDataHandler, times(1)).getIngredientById("123");
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldAddANewIngredient() throws Exception {
        Ingredient test = new Ingredient();
        test.setName("Melon");
        String url = "/ingredients";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(test);

        mockMvc.perform(post(url).contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        ArgumentCaptor<Ingredient> argumentCaptor = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientsDataHandler, times(1)).addIngredient(argumentCaptor.capture());
        Assertions.assertEquals("Melon", argumentCaptor.getValue().getName());
        Assertions.assertEquals("melon", argumentCaptor.getValue().getSearchName());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldUpdateIngredient() throws Exception {
        Ingredient update = new Ingredient("123");
        update.setName("Melon");
        String url = "/ingredients";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(update);

        mockMvc.perform(put(url).contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        ArgumentCaptor<Ingredient> argumentCaptor = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientsDataHandler, times(1)).updateIngredient(argumentCaptor.capture());
        Assertions.assertEquals("Melon", argumentCaptor.getValue().getName());
        Assertions.assertEquals("123", argumentCaptor.getValue().getId());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldDeleteIngredient() throws Exception {
        mockMvc.perform(delete("/ingredients/123"))
                .andExpect(status().isOk());
        verify(ingredientsDataHandler, times(1)).deleteIngredient("123");
    }
}