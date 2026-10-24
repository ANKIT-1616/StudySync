package com.studysync.service;

import com.studysync.model.Task;
import com.studysync.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
public class StudyPlanService {

    private final TaskRepository taskRepository;

    public StudyPlanService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> generateRebalancedPlan() {
        LocalDate today = LocalDate.now();

        return taskRepository.findAll()
                .stream()
                .filter(task -> !task.isCompleted())
                .sorted(
                        Comparator
                                .comparingInt((Task task) ->
                                        calculatePriorityScore(task, today))
                                .reversed()
                                .thenComparing(
                                        Task::getDeadline,
                                        Comparator.nullsLast(
                                                Comparator.naturalOrder()
                                        )
                                )
                )
                .toList();
    }

    private int calculatePriorityScore(Task task, LocalDate today) {

        int score = 0;

        // Importance score
        String priority = task.getPriority();

        if (priority != null) {
            score += switch (priority.toUpperCase()) {
                case "HIGH" -> 30;
                case "MEDIUM" -> 20;
                case "LOW" -> 10;
                default -> 0;
            };
        }

        // Deadline urgency score
        LocalDate deadline = task.getDeadline();

        if (deadline != null) {
            long daysRemaining = ChronoUnit.DAYS.between(
                    today, deadline
            );

            if (daysRemaining < 0) {
                score += 50; // Overdue
            } else if (daysRemaining == 0) {
                score += 40; // Due today
            } else if (daysRemaining == 1) {
                score += 30; // Due tomorrow
            } else if (daysRemaining <= 3) {
                score += 20; // Due within three days
            } else if (daysRemaining <= 7) {
                score += 10; // Due within a week
            }
        }

        return score;
    }
public StudyPlanProposal generateProposal(String reason) {
    List<Task> proposedTasks = generateRebalancedPlan();

    return new StudyPlanProposal(
            LocalDate.now(),
            reason,
            proposedTasks.size(),
            proposedTasks
    );
}}

