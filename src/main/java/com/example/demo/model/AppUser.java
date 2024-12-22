package com.example.demo.model;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.demo.model.leetCodeApiService.Question;
import com.example.demo.model.leetCodeApiService.TopicTag;
import com.example.demo.service.LeetCodeApiService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @OneToMany
    private List<TaskRecord> records = new ArrayList<>();

    public AppUser() {
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<TaskRecord> getRecords() {
        return records;
    }

    public void addRecord(TaskRecord record) {
        records.add(record);
    }

    public Map<String, Long> getSolvedProblemsByDifficulty(LeetCodeApiService service) {
        return records.stream()
                .map(record -> service.getQuestion(record.getTaskName()))
                .collect(Collectors.groupingBy(Question::getDifficulty, Collectors.counting()));
    }

    public Map<String, Long> getSolvedProblemsByTopic(LeetCodeApiService service) {
        return records.stream()
                .flatMap(record -> service.getQuestion(record.getTaskName()).getTopicTags().stream())
                .collect(Collectors.groupingBy(TopicTag::getName, Collectors.counting()));
    }

    public Map<DayOfWeek, Long> getSolvedProblemsByDayOfWeek() {
        return records.stream()
                .collect(Collectors.groupingBy(record -> record.getDate().getDayOfWeek(), Collectors.counting()));
    }
}
