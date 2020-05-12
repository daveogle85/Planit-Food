package com.planitfood.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.planitfood.data.MealDataHandler;
import com.planitfood.models.Meal;
import com.planitfood.restApi.PlanitFoodApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
@WebMvcTest(MealController.class)
public class MealsTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MealDataHandler mealDataHandler;

    @Test
    public void shouldReturnAll() throws Exception {
        String url = "/meals";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(mealDataHandler, times(1)).getAllMeals();
    }

    @Test
    public void shouldSearchMealsByName() throws Exception {
        String url = "/meals";
        ArgumentCaptor<String> searchName = ArgumentCaptor.forClass(String.class);

        mockMvc.perform(get(url + "?searchName=bob")).andExpect(status().isOk());
        verify(mealDataHandler, times(1)).getMealsByQuery(
                searchName.capture()
        );
        Assertions.assertEquals("bob", searchName.getValue());
    }

    @Test
    public void shouldReturnMealById() throws Exception {
        String url = "/meals/123";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(mealDataHandler, times(1)).getMealById("123");
    }

    @Test
    public void shouldAddANewMeal() throws Exception {
        Meal test = new Meal();
        test.setName("Pie");
        String url = "/meals";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(test);

        mockMvc.perform(post(url).contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        ArgumentCaptor<Meal> argumentCaptor = ArgumentCaptor.forClass(Meal.class);
        verify(mealDataHandler, times(1)).addMeal(argumentCaptor.capture());
        Assertions.assertEquals("Pie", argumentCaptor.getValue().getName());
        Assertions.assertEquals("pie", argumentCaptor.getValue().getSearchName());
    }

    @Test
    public void shouldUpdateMeal() throws Exception {
        Meal update = new Meal("123");
        update.setName("Pie");
        String url = "/meals";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(update);

        mockMvc.perform(put(url).contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        ArgumentCaptor<Meal> argumentCaptor = ArgumentCaptor.forClass(Meal.class);
        verify(mealDataHandler, times(1)).updateMeal(argumentCaptor.capture());
        Assertions.assertEquals("Pie", argumentCaptor.getValue().getName());
        Assertions.assertEquals("123", argumentCaptor.getValue().getId());
    }

    @Test
    public void shouldDeleteMeal() throws Exception {
        mockMvc.perform(delete("/meals/123"))
                .andExpect(status().isOk());
        verify(mealDataHandler, times(1)).deleteMeal("123");
    }
}
