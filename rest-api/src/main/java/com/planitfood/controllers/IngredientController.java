package com.planitfood.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IngredientController {

    @GetMapping("/")
    public @ResponseBody String greeting() {
        return "Hello, World";
    }
}
