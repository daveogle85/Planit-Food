package com.planitfood.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.planitfood.data.ListDataHandler;
import com.planitfood.models.CustomList;
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
@WebMvcTest(ListController.class)
public class ListsTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ListDataHandler listDataHandler;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .build();
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldReturnListById() throws Exception {
        String url = "/lists/id/123";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(listDataHandler, times(1)).getListById("123");
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldReturnListByName() throws Exception {
        String url = "/lists/name/myName";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(listDataHandler, times(1)).getListByName("myName", true);
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldReturnAll() throws Exception {
        String url = "/lists";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

        verify(listDataHandler, times(1)).getAllLists();
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldAddANewList() throws Exception {
        CustomList test = new CustomList();
        test.setName("test");
        String url = "/lists";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(test);

        mockMvc.perform(post(url).contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        ArgumentCaptor<CustomList> argumentCaptor = ArgumentCaptor.forClass(CustomList.class);
        verify(listDataHandler, times(1)).addList(argumentCaptor.capture());
        Assertions.assertEquals("test", argumentCaptor.getValue().getName());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldUpdateList() throws Exception {
        Meal toAdd = new Meal("123");
        toAdd.setName("Pie");
        String url = "/lists/456/meals";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(toAdd);

        mockMvc.perform(put(url).contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        ArgumentCaptor<String> id = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Meal> meal = ArgumentCaptor.forClass(Meal.class);
        verify(listDataHandler, times(1)).updateMealsInList(id.capture(), meal.capture());
        Assertions.assertEquals("456", id.getValue());
        Assertions.assertEquals("123", meal.getValue().getId());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldDeleteDish() throws Exception {
        mockMvc.perform(delete("/lists/123"))
                .andExpect(status().isOk());
        verify(listDataHandler, times(1)).deleteList("123");
    }

}
