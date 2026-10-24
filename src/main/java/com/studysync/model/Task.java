package com.studysync.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "study_tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Task title is required")
    private String title;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotNull(message = "Deadline is required")
    private LocalDate deadline;

    @NotBlank(message = "Priority is required")
    private String priority;

    private boolean completed = false;
    private Integer planOrder;

    public Task() {
    }

    public Task(String title, String subject, LocalDate deadline, String priority) {
        this.title = title;
        this.subject = subject;
        this.deadline = deadline;
        this.priority = priority;
        this.completed = false;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }public Integer getPlanOrder() {
    return planOrder;
}

public void setPlanOrder(Integer planOrder) {
    this.planOrder = planOrder;
}

}