package com.planitfood.controllers;

import com.planitfood.data.DayDataHandler;
import com.planitfood.models.Day;
import com.planitfood.restApi.PlanitFoodApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = PlanitFoodApplication.class)
@WebMvcTest(DayController.class)
public class DaysTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DayDataHandler dayDataHandler;

    @Test
    public void shouldReturnDayByRange() throws Exception {
        String url = "/days";
        ArgumentCaptor<LocalDate> startDate = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<LocalDate> endDate = ArgumentCaptor.forClass(LocalDate.class);

        mockMvc.perform(get(url + "?startDate=1985-10-08&endDate=1986-11-10")).andExpect(status().isOk());
        verify(dayDataHandler, times(1)).getDayByRange(
                startDate.capture(),
                endDate.capture()
        );
        Assertions.assertEquals("1985-10-08", startDate.getValue().toString());
        Assertions.assertEquals("1986-11-10", endDate.getValue().toString());
    }

    @Test
    public void shouldReturnDayByDate() throws Exception {
        String url = "/days";
        ArgumentCaptor<LocalDate> startDate = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<LocalDate> endDate = ArgumentCaptor.forClass(LocalDate.class);

        mockMvc.perform(get(url + "?startDate=1985-10-08")).andExpect(status().isOk());
        verify(dayDataHandler, times(1)).getDayByRange(
                startDate.capture(),
                endDate.capture()
        );
        Assertions.assertEquals("1985-10-08", startDate.getValue().toString());
        Assertions.assertNull(endDate.getValue());
    }

    @Test
    public void shouldAddANewDay() throws Exception {
        String url = "/days";

        mockMvc.perform(post(url).contentType(APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\" : null,\n" +
                        "  \"date\" : \"2020-12-01\",\n" +
                        "  \"meal\" : {\n" +
                        "    \"id\" : \"1\",\n" +
                        "    \"name\" : \"Pie\",\n" +
                        "    \"searchName\" : \"pie\",\n" +
                        "    \"dishes\" : null,\n" +
                        "    \"notes\" : null\n" +
                        "  },\n" +
                        "  \"notes\" : null\n" +
                        "}"))
                .andExpect(status().isOk());

        ArgumentCaptor<Day> argumentCaptor = ArgumentCaptor.forClass(Day.class);
        verify(dayDataHandler, times(1)).addDay(argumentCaptor.capture());
        Assertions.assertEquals("DECEMBER-2020", argumentCaptor.getValue().getId());
        Assertions.assertEquals("2020-12-01", argumentCaptor.getValue().getDate().toString());
        Assertions.assertEquals("1", argumentCaptor.getValue().getMeal().getId());
    }

    @Test
    public void shouldUpdateDay() throws Exception {
        String url = "/days";

        mockMvc.perform(put(url).contentType(APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\" : null,\n" +
                        "  \"date\" : \"2020-12-01\",\n" +
                        "  \"meal\" : {\n" +
                        "    \"id\" : \"1\",\n" +
                        "    \"name\" : \"Pie\",\n" +
                        "    \"searchName\" : \"pie\",\n" +
                        "    \"dishes\" : null,\n" +
                        "    \"notes\" : null\n" +
                        "  },\n" +
                        "  \"notes\" : null\n" +
                        "}"))
                .andExpect(status().isOk());

        ArgumentCaptor<Day> argumentCaptor = ArgumentCaptor.forClass(Day.class);
        verify(dayDataHandler, times(1)).updateDay(argumentCaptor.capture());
        Assertions.assertEquals("DECEMBER-2020", argumentCaptor.getValue().getId());
        Assertions.assertEquals("2020-12-01", argumentCaptor.getValue().getDate().toString());
        Assertions.assertEquals("1", argumentCaptor.getValue().getMeal().getId());
    }

    @Test
    public void shouldDeleteDay() throws Exception {
        mockMvc.perform(delete("/days/2020-12-01"))
                .andExpect(status().isOk());
        verify(dayDataHandler, times(1)).deleteDay(LocalDate.of(2020, 12, 01));
    }
}
