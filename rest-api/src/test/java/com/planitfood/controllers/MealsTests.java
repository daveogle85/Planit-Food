package com.planitfood.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.planitfood.data.MealDataHandler;
import com.planitfood.models.Meal;
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
@WebMvcTest(MealController.class)
public class MealsTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MealDataHandler mealDataHandler;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .build();
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldReturnAll() throws Exception {
        String url = "/meals";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(mealDataHandler, times(1)).getAllMeals();
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldSearchMealsByName() throws Exception {
        String url = "/meals";
        ArgumentCaptor<String> searchName = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dishId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> addDishes = ArgumentCaptor.forClass(Boolean.class);

        mockMvc.perform(get(url + "?searchName=bob")).andExpect(status().isOk());
        verify(mealDataHandler, times(1)).getMealsByQuery(
                searchName.capture(),
                dishId.capture(),
                addDishes.capture()
        );
        Assertions.assertEquals("bob", searchName.getValue());
        Assertions.assertNull(dishId.getValue());
        Assertions.assertTrue(addDishes.getValue());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldReturnMealById() throws Exception {
        String url = "/meals/123";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(mealDataHandler, times(1)).getMealById("123");
    }

    @Test
    @WithMockUser(username = "testUser")
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
    @WithMockUser(username = "testUser")
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
    @WithMockUser(username = "testUser")
    public void shouldDeleteMeal() throws Exception {
        mockMvc.perform(delete("/meals/123"))
                .andExpect(status().isOk());
        verify(mealDataHandler, times(1)).deleteMeal("123");
    }
}
