package com.example.demo.model;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Duration;
import java.time.LocalDateTime;
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

    public Map<String, Long> getSolvedProblemsByDifficulty(LeetCodeApiService service, Duration period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.minus(period);
        return records.stream()
                .filter(record -> record.getDate().isAfter(startTime))
                .map(record -> service.getQuestion(record.getTaskName()))
                .collect(Collectors.groupingBy(Question::getDifficulty, Collectors.counting()));
    }

    public Map<String, Long> getSolvedProblemsByTopic(LeetCodeApiService service, Duration period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.minus(period);
        return records.stream()
                .filter(record -> record.getDate().isAfter(startTime))
                .flatMap(record -> service.getQuestion(record.getTaskName()).getTopicTags().stream())
                .collect(Collectors.groupingBy(TopicTag::getName, Collectors.counting()));
    }

    public Map<DayOfWeek, Long> getSolvedProblemsByDayOfWeek() {
        return records.stream()
                .collect(Collectors.groupingBy(record -> record.getDate().getDayOfWeek(), Collectors.counting()));
    }

    public List<Question> getRecommendedTasksSlugs(LeetCodeApiService service) {
        List<TaskRecord> sortedRecords = records.stream()
                .sorted(Comparator.comparing(TaskRecord::getTryCounter).reversed()
                        .thenComparing(TaskRecord::getDate))
                .collect(Collectors.toList());

        List<String> taskNames = sortedRecords.stream()
                .map(TaskRecord::getTaskName)
                .collect(Collectors.toList());
        
        List<Question> similarQuestionsList = new ArrayList<>();
        for (String taskName : taskNames) {
            if(similarQuestionsList.size() >= 6) {
                break;
            }
            Question question = service.getQuestion(taskName);
            List<String> slugs = question.getSimilarQuestionSlugs();
            for (String slug : slugs) {
                if (similarQuestionsList.size() >= 6 || taskNames.contains(slug)) {
                    break;
                }
                Question similarQuestion = service.getQuestion(slug);

                similarQuestionsList.add(similarQuestion);
            }
        }
        return similarQuestionsList;
    }

    public List<Double> getAverageTimeToSolve(LeetCodeApiService service, Duration period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.minus(period);
        Map<String, DoubleSummaryStatistics> statsMap = new HashMap<>();
        records.stream()
                .filter(record -> record.getDate().isAfter(startTime))
                .forEach(record -> {
                    Question question = service.getQuestion(record.getTaskName());
                    String difficulty = question.getDifficulty();
                    long duration = record.getDuration().toMinutes();

                    statsMap.computeIfAbsent(difficulty, k -> new DoubleSummaryStatistics()).accept(duration);
                });
        double easyAvg = statsMap.getOrDefault("easy", new DoubleSummaryStatistics()).getAverage();
        double mediumAvg = statsMap.getOrDefault("medium", new DoubleSummaryStatistics()).getAverage();
        double hardAvg = statsMap.getOrDefault("hard", new DoubleSummaryStatistics()).getAverage();
        return List.of(easyAvg, mediumAvg, hardAvg);
    }

    public List<Long> getFirstAttemptStats(LeetCodeApiService service, Duration period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.minus(period);

        long firstAttemptCount = records.stream()
                .filter(record -> record.getDate().isAfter(startTime))
                .filter(record -> record.getTryCounter() == 1) // Предполагается, что поле tryCounter указывает количество попыток
                .count();

        long notFirstAttemptCount = records.stream()
                .filter(record -> record.getDate().isAfter(startTime))
                .filter(record -> record.getTryCounter() > 1)
                .count();

        return List.of(firstAttemptCount, notFirstAttemptCount);
    }
}
