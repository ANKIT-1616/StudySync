package com.studysync.service;

import com.studysync.model.Task;
import com.studysync.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class StudyPlanService {

    private final TaskRepository taskRepository;

    public StudyPlanService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> generateRebalancedPlan() {

        List<Task> pendingTasks = taskRepository.findAll()
                .stream()
                .filter(task -> !task.isCompleted())
                .sorted(
                    Comparator
                        .comparingInt(this::priorityScore)
                        .reversed()
                        .thenComparing(Task::getDeadline)
                )
                .toList();

        return pendingTasks;
    }

    private int priorityScore(Task task) {
        int score = switch (task.getPriority().toUpperCase()) {
            case "HIGH" -> 3;
            case "MEDIUM" -> 2;
            case "LOW" -> 1;
            default -> 0;
        };

        if (task.getDeadline().isBefore(LocalDate.now().plusDays(2))) {
            score += 2;
        }

        return score;
    }
}