package com.studysync;

import com.studysync.model.Task;
import com.studysync.repository.TaskRepository;
import com.studysync.service.StudyPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudyPlanServiceTests {

    @Autowired
    private StudyPlanService studyPlanService;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void cleanDatabase() {
        taskRepository.deleteAll();
    }

    @Test
    void overdueTaskShouldAppearBeforeTaskWithLaterDeadline() {
        Task overdueTask = new Task();
        overdueTask.setTitle("Overdue Assignment");
        overdueTask.setSubject("Java");
        overdueTask.setDeadline(LocalDate.now().minusDays(1));
        overdueTask.setPriority("LOW");
        taskRepository.save(overdueTask);

        Task futureTask = new Task();
        futureTask.setTitle("Future Assignment");
        futureTask.setSubject("Java");
        futureTask.setDeadline(LocalDate.now().plusDays(10));
        futureTask.setPriority("LOW");
        taskRepository.save(futureTask);

        List<Task> plan = studyPlanService.generateRebalancedPlan();

        assertEquals(2, plan.size());
        assertEquals("Overdue Assignment", plan.get(0).getTitle());
        assertEquals("Future Assignment", plan.get(1).getTitle());
    }

    @Test
    void completedTasksShouldNotAppearInStudyPlan() {
        Task completedTask = new Task();
        completedTask.setTitle("Completed Assignment");
        completedTask.setSubject("Java");
        completedTask.setDeadline(LocalDate.now().plusDays(1));
        completedTask.setPriority("HIGH");
        completedTask.setCompleted(true);
        taskRepository.save(completedTask);

        List<Task> plan = studyPlanService.generateRebalancedPlan();

        assertTrue(plan.isEmpty());
    }
}