package com.geekbrains.repeatapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @GetMapping
    @RequestMapping("/students")
    public String showIndexPage() {
        return "index";
    }
}
