package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Record;
import com.example.demo.repository.RecordRepository;


@Controller
public class RecordController {

    @Autowired
    private RecordRepository recordRepository;

    @GetMapping("/records")
    public String getAllRecords(Model model) {
        model.addAttribute("records", recordRepository.findAll());
        return "records";
    }

    @GetMapping("/records/{username}")
    public String getAllRecordsByUser(Model model, @PathVariable String username) {
        model.addAttribute("records", recordRepository.findByUsername(username));
        return "records";
    }

    @PostMapping("/add")
    public String makeNewRecord(@RequestParam String username, @RequestParam String content) {
        Record newRecord = new Record(username, content);
        recordRepository.save(newRecord);
        return "redirect:/dashboard";
    }
}
