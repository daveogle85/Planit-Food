package com.planitfood.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.planitfood.controllers.IngredientController;
import com.planitfood.data.DishDataHandler;
import com.planitfood.data.IngredientsDataHandler;
import com.planitfood.data.MealDataHandler;
import com.planitfood.enums.DishType;
import com.planitfood.models.Day;
import com.planitfood.models.Dish;
import com.planitfood.models.Ingredient;
import com.planitfood.models.Meal;
import com.planitfood.restApi.PlanitFoodApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = PlanitFoodApplication.class)
@WebMvcTest(IngredientController.class)
public class Populate {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private IngredientsDataHandler ingredientsDataHandler;

    @Autowired
    private DishDataHandler dishDataHandler;

    @Autowired
    private MealDataHandler mealDataHandler;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .build();
    }

    private void makeRequest(String requestJson, String url) throws Exception {
        mockMvc.perform(post(url).contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldAddNewIngredients() throws Exception {
        Ingredient test1 = new Ingredient();
        Ingredient test2 = new Ingredient();
        Ingredient test3 = new Ingredient();
        Ingredient test4 = new Ingredient();

        test1.setName("Test Ingredient 1");
        test2.setName("Test Ingredient 2");
        test3.setName("Test Ingredient 3");
        test4.setName("Test Ingredient 4");

        String url = "/ingredients";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        makeRequest(ow.writeValueAsString(test1), url);
        makeRequest(ow.writeValueAsString(test2), url);
        makeRequest(ow.writeValueAsString(test3), url);
        makeRequest(ow.writeValueAsString(test4), url);
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldAddNewDishes() throws Exception {
        String url = "/dishes";
        List<Ingredient> ingredients =ingredientsDataHandler.getAllIngredients();
        ArrayList<Dish> dishes = new ArrayList<Dish>();
        for(int i = 0; i < 8; i++) {
            dishes.add(new Dish());
        }

        for(int i = 0; i < dishes.size(); i++) {
           Dish d = dishes.get(i);
           d.setName("Test Dish " + (i + 1));
           if(i % 3 == 0) {
               d.setDishType(DishType.MAIN);
           } else {
               d.setDishType(DishType.SIDE);
           }
           d.setNotes("Some notes");
           d.setIngredients(ingredients);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        for(Dish dish: dishes) {
            makeRequest(ow.writeValueAsString(dish), url);
        }
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldAddNewMeals() throws Exception {
        Random rand = new Random();
        String url = "/meals";
        List<Dish> dishes = dishDataHandler.getAllDishes();

        ArrayList<Meal> meals = new ArrayList<Meal>();
        for(int i = 0; i < 5; i++) {
            meals.add(new Meal());
        }

        for(int i = 0; i < meals.size(); i++) {
            Meal m = meals.get(i);
            m.setName("Test Meal " + (i + 1));
            m.setNotes("Some notes");
            List<Dish> dishList = new ArrayList<Dish>();
            for(int j = 0; j < 3; j++) {
                Dish d = dishes.get(rand.nextInt(dishes.size() - 1));
                dishList.add(d);
            }
            m.setDishes(dishList);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        for(Meal meal: meals) {
            makeRequest(ow.writeValueAsString(meal), url);
        }
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldAddNewDays() throws Exception {
        String url = "/days";
        Random rand = new Random();
        List<Meal> meals = mealDataHandler.getAllMeals();

        ArrayList<Day> days = new ArrayList<Day>();
        for(int i = 0; i < 14; i++) {
            days.add(new Day());
        }

        LocalDate start = LocalDate.now().minusDays(7);

        for(int i = 0; i < days.size(); i++) {
            Day d = days.get(i);
            d.setDate(start);
            start = start.plusDays(1);
            d.setNotes("Some notes");
            Meal m = meals.get(rand.nextInt(meals.size() - 1));
            d.setMeal(m);
        }

        for(Day day: days) {
            makeRequest(convertDayToJson(day), url);
        }
    }

    private String convertDayToJson(Day day) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return "{\n" +
                "  \"id\" : null,\n" +
                "  \"date\" : \"" + day.getDate().toString() + "\",\n" +
                "  \"meal\" : {\n" +
                "    \"id\" : \"" + day.getMeal().getId() + "\",\n" +
                "    \"name\" : \"" + day.getMeal().getName() + "\",\n" +
                "    \"searchName\" : \"" + day.getMeal().getSearchName() + "\",\n" +
                "    \"dishes\" : " + ow.writeValueAsString(day.getMeal().getDishes()) + ",\n" +
                "    \"notes\" : \"" + day.getMeal().getNotes() + "\"\n" +
                "  },\n" +
                "  \"notes\" : \"" + day.getNotes() + "\"\n" +
                "}";
    }
}
