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
        String priority = task.getPriority();

        if (priority != null) {
            score += switch (priority.toUpperCase()) {
                case "HIGH" -> 30;
                case "MEDIUM" -> 20;
                case "LOW" -> 10;
                default -> 0;
            };
        }

        LocalDate deadline = task.getDeadline();

        if (deadline != null) {
            long daysRemaining = ChronoUnit.DAYS.between(today, deadline);

            if (daysRemaining < 0) {
                score += 50;
            } else if (daysRemaining == 0) {
                score += 40;
            } else if (daysRemaining == 1) {
                score += 30;
            } else if (daysRemaining <= 3) {
                score += 20;
            } else if (daysRemaining <= 7) {
                score += 10;
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
    }

    public List<Task> acceptStudyPlan() {
        List<Task> proposedTasks = generateRebalancedPlan();

        for (int i = 0; i < proposedTasks.size(); i++) {
            proposedTasks.get(i).setPlanOrder(i + 1);
        }

        return taskRepository.saveAll(proposedTasks);
    }
}