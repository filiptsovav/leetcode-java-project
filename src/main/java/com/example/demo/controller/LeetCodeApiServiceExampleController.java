package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.leetCodeApiService.Question;
import com.example.demo.service.LeetCodeApiService;

@RestController
public class LeetCodeApiServiceExampleController {
    @Autowired
    private LeetCodeApiService leetCodeApiService;

    @GetMapping("/getQuestion/{questionName}")
    public Question sendRequest(@PathVariable String questionName) {
        return leetCodeApiService.getQuestion(questionName); //TODO can throw exception
    }
}
