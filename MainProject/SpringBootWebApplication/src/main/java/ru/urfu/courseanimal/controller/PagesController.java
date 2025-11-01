package ru.urfu.courseanimal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {
    @GetMapping("/") public String index() { return "index"; }
    @GetMapping("/about") public String about() { return "about"; }
}