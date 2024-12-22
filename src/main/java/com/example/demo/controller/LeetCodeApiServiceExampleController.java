package com.example.demo.controller;

import com.example.demo.model.AppUser;
import com.example.demo.model.TaskRecord;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.leetCodeApiService.Question;
import com.example.demo.service.LeetCodeApiService;

@RestController
public class LeetCodeApiServiceExampleController {
    @Autowired
    private LeetCodeApiService leetCodeApiService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getQuestion/{questionName}")
    public Question sendRequest(@PathVariable String questionName) {
        return leetCodeApiService.getQuestion(questionName); //TODO can throw exception
    }

    @PostMapping("/submitTask")
    public String submitTask(
            @RequestParam String taskName,
            @RequestParam String taskTime,
            @RequestParam String completionDate,
            HttpServletRequest request,
            Model model) {

        Question question;
        try {
            question = leetCodeApiService.getQuestion(taskName);
        } catch (Exception e) {
            return "redirect:/taskChosen?error=true";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser currentUser = (AppUser) authentication.getPrincipal();

        TaskRecord record = new TaskRecord(taskName);
        currentUser.addRecord(record);
        userRepository.save(currentUser);
        return "redirect:/taskChosen?success=true";
    }

}
