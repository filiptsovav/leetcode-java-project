package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;


@Controller
public class DemoController {
    @GetMapping("/")
    public String redirectToIndex() {
        return "forward:/login";
        }


    @GetMapping("/dashboard")
    public String showDashboard() {
        return "forward:/dashboard.html";
    }

    @GetMapping("/statistics")
    public String showStatistics() {
        return "forward:/statistics.html";
    }

    @GetMapping("/taskChosen")
    public String taskChosen() {
        return "taskChosen";
    }


    @GetMapping("/taskSuggestion")
    public String taskSuggestion() {
        return "forward:/taskSuggestion.html";
    }

}