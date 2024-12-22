package com.example.demo.controller;

import com.example.demo.model.AppUser;
import com.example.demo.model.TaskRecord;
import com.example.demo.repository.RecordRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.leetCodeApiService.Question;
import com.example.demo.service.LeetCodeApiService;

@Controller
public class LeetCodeApiServiceExampleController {
    @Autowired
    private LeetCodeApiService leetCodeApiService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordRepository recordRepository;

    @GetMapping("/getQuestion/{questionName}")
    public Question sendRequest(@PathVariable String questionName) {
        return leetCodeApiService.getQuestion(questionName); //TODO can throw exception
    }

    @PostMapping("/taskChosen")
    @Transactional
    public String submitTask(
            @RequestParam String taskName,
            @RequestParam String taskTime,
            @RequestParam String completionDate,
            HttpServletRequest request,
            Model model) {

        try {
            leetCodeApiService.getQuestion(taskName);
        } catch (Exception e) {
            return "redirect:/taskChosen?error=true";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User currentUser =
                (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        AppUser appUser = userRepository.findByUsername(currentUser.getUsername());
        TaskRecord record = new TaskRecord(taskName);
        recordRepository.save(record);
        appUser.addRecord(record);
        userRepository.save(appUser);
        return "redirect:/taskChosen?success=true";
    }

}
