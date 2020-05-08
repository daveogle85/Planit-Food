package com.planitfood.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.planitfood.data.DishDataHandler;
import com.planitfood.enums.DishType;
import com.planitfood.models.Dish;
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
@WebMvcTest(DishController.class)
public class DishesTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DishDataHandler dishDataHandler;

    @Test
    public void shouldReturnAll() throws Exception {
        String url = "/dishes";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(dishDataHandler, times(1)).getAllDishes();
    }

    @Test
    public void shouldSearchDishesByName() throws Exception {
        String url = "/dishes";
        ArgumentCaptor<String> searchName = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> ingredientId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<DishType> dishType = ArgumentCaptor.forClass(DishType.class);

        // dishByName
        mockMvc.perform(get(url + "?searchName=bob")).andExpect(status().isOk());
        verify(dishDataHandler, times(1)).getDishesByQuery(
                searchName.capture(),
                ingredientId.capture(),
                dishType.capture()
        );
        Assertions.assertEquals("bob", searchName.getValue());
        Assertions.assertNull(ingredientId.getValue());
        Assertions.assertNull(dishType.getValue());
    }

    @Test
    public void shouldSearchDishesByIngredientId() throws Exception {
        String url = "/dishes";
        ArgumentCaptor<String> searchName = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> ingredientId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<DishType> dishType = ArgumentCaptor.forClass(DishType.class);

        // dishByName
        mockMvc.perform(get(url + "?ingredientId=123")).andExpect(status().isOk());
        verify(dishDataHandler, times(1)).getDishesByQuery(
                searchName.capture(),
                ingredientId.capture(),
                dishType.capture()
        );
        Assertions.assertNull(searchName.getValue());
        Assertions.assertEquals("123", ingredientId.getValue());
        Assertions.assertNull(dishType.getValue());
    }

    @Test
    public void shouldSearchDishesByDishType() throws Exception {
        String url = "/dishes";
        ArgumentCaptor<String> searchName = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> ingredientId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<DishType> dishType = ArgumentCaptor.forClass(DishType.class);

        // dishByName
        mockMvc.perform(get(url + "?dishType=SIDE")).andExpect(status().isOk());
        verify(dishDataHandler, times(1)).getDishesByQuery(
                searchName.capture(),
                ingredientId.capture(),
                dishType.capture()
        );
        Assertions.assertNull(searchName.getValue());
        Assertions.assertNull(ingredientId.getValue());
        Assertions.assertEquals(DishType.SIDE, dishType.getValue());
    }

    @Test
    public void shouldSearchDishesByMultipleQueries() throws Exception {
        String url = "/dishes";
        ArgumentCaptor<String> searchName = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> ingredientId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<DishType> dishType = ArgumentCaptor.forClass(DishType.class);

        // dishByName
        mockMvc.perform(get(url + "?dishType=MAIN&ingredientId=123")).andExpect(status().isOk());
        verify(dishDataHandler, times(1)).getDishesByQuery(
                searchName.capture(),
                ingredientId.capture(),
                dishType.capture()
        );
        Assertions.assertNull(searchName.getValue());
        Assertions.assertEquals("123", ingredientId.getValue());
        Assertions.assertEquals(DishType.MAIN, dishType.getValue());
    }

    @Test
    public void shouldReturnDishById() throws Exception {
        String url = "/dishes/123";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(dishDataHandler, times(1)).getDishById("123");
    }

    @Test
    public void shouldAddANewDish() throws Exception {
        Dish test = new Dish();
        test.setName("Pie");
        String url = "/dishes";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(test);

        mockMvc.perform(post(url).contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        ArgumentCaptor<Dish> argumentCaptor = ArgumentCaptor.forClass(Dish.class);
        verify(dishDataHandler, times(1)).addDish(argumentCaptor.capture());
        Assertions.assertEquals("Pie", argumentCaptor.getValue().getName());
        Assertions.assertEquals("pie", argumentCaptor.getValue().getSearchName());
    }

    @Test
    public void shouldUpdateDish() throws Exception {
        Dish update = new Dish("123");
        update.setName("Pie");
        String url = "/dishes";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(update);

        mockMvc.perform(put(url).contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        ArgumentCaptor<Dish> argumentCaptor = ArgumentCaptor.forClass(Dish.class);
        verify(dishDataHandler, times(1)).updateDish(argumentCaptor.capture());
        Assertions.assertEquals("Pie", argumentCaptor.getValue().getName());
        Assertions.assertEquals("123", argumentCaptor.getValue().getId());
    }

    @Test
    public void shouldDeleteDish() throws Exception {
        mockMvc.perform(delete("/dishes/123"))
                .andExpect(status().isOk());
        verify(dishDataHandler, times(1)).deleteDish("123");
    }
}
