package com.example.Lab5.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {

    @RequestMapping("/")
    public String hello() {
        return "Hello! I'm running";
    }
}
