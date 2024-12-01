package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;


//видимо нужен чтобы отвечать на запросы
@Controller
public class DemoController {
    @GetMapping("/")
    public String redirectToIndex() {
        return "forward:/index.html";
        }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password) {
        // Пока авторизация фиктивная, пропускаем всех пользователей
        if (!username.isEmpty() && !password.isEmpty()) {
            return "redirect:/dashboard"; // Перенаправляем на dashboard
        } else {
            return "redirect:/?error=true"; // Возвращаемся на главную страницу с ошибкой
        }
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
        return "forward:/taskChosen.html";
    }

    @GetMapping("/taskSuggestion")
    public String taskSuggestion() {
        return "forward:/taskSuggestion.html";
    }

    @GetMapping("/addform")
    public String redirectToCreate() {
        return "forward:/create.html";
    }
}