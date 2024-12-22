package com.example.demo.controller;

import com.example.demo.model.AppUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.LeetCodeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class StatisticsController {

    @Autowired
    private LeetCodeApiService leetCodeApiService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/statistics")
    public String getStatistics(@RequestParam(required = false) String timeframe, Model model) {
        if (timeframe == null || timeframe.isEmpty()) {
            timeframe = "month"; // Значение по умолчанию
        }

        System.out.println("Selected timeframe: " + timeframe);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User currentUser =
                (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        AppUser appUser = userRepository.findByUsername(currentUser.getUsername());

        Duration period = calculatePeriod(timeframe);

        Map<String, Long> difficultyStats = appUser.getSolvedProblemsByDifficulty(leetCodeApiService, period);
        Map<String, Long> topicStats = appUser.getSolvedProblemsByTopic(leetCodeApiService, period);
        Map<DayOfWeek, Long> dayOfWeekStats = appUser.getSolvedProblemsByDayOfWeek();
        List<Double> avgTime = appUser.getAverageTimeToSolve(leetCodeApiService, period);
        List<Long> firstAttempt = appUser.getFirstAttemptStats(leetCodeApiService, period);

        System.out.println("Timeframe: " + timeframe);
        System.out.println("Difficulty Stats: " + difficultyStats);
        System.out.println("Topic Stats: " + topicStats);
        System.out.println("Day of Week Stats: " + dayOfWeekStats);

        model.addAttribute("difficultyStats", difficultyStats);
        model.addAttribute("topicStats", topicStats);
        model.addAttribute("dayOfWeekStats", dayOfWeekStats);
        model.addAttribute("avgTime", avgTime);
        model.addAttribute("firstAttempt", firstAttempt);

        return "statistics";
    }

    private Duration calculatePeriod(String timeframe) {
        return switch (timeframe) {
            case "week" -> Duration.ofDays(7);
            case "month" -> Duration.ofDays(30);
            case "year" -> Duration.ofDays(365);
            default -> throw new IllegalArgumentException("Unknown timeframe: " + timeframe);
        };
    }
}