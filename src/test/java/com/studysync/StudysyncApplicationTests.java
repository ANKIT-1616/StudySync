package com.studysync;

import com.studysync.exception.TaskNotFoundException;
import com.studysync.model.Task;
import com.studysync.service.TaskService;
import com.studysync.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudysyncApplicationTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void cleanDatabase() {
        taskRepository.deleteAll();
    }

    @Test
    void createTaskShouldSaveTask() {
        Task task = new Task();
        task.setTitle("Study Java");
        task.setSubject("Programming");
        task.setDeadline(LocalDate.now().plusDays(2));
        task.setPriority("HIGH");

        Task savedTask = taskService.createTask(task);

        assertNotNull(savedTask.getId());
        assertEquals("Study Java", savedTask.getTitle());
    }

    @Test
    void getAllTasksShouldReturnSavedTasks() {
        Task task = new Task();
        task.setTitle("Study SQL");
        task.setSubject("Database");

task.setDeadline(LocalDate.now().plusDays(2));

        task.setPriority("MEDIUM");
        taskService.createTask(task);

        assertEquals(1, taskService.getAllTasks().size());
    }

    @Test
    void getTaskByIdShouldReturnCorrectTask() {
        Task task = new Task();
        task.setTitle("Study HTML");
        task.setSubject("Web Development");
		task.setDeadline(LocalDate.now().plusDays(2));
        task.setPriority("LOW");
        Task savedTask = taskService.createTask(task);

        Task foundTask = taskService.getTaskById(savedTask.getId());

        assertEquals(savedTask.getId(), foundTask.getId());
        assertEquals("Study HTML", foundTask.getTitle());
    }

    @Test
    void updateTaskShouldChangeTaskDetails() {
        Task task = new Task();
        task.setTitle("Old Title");
		task.setDeadline(LocalDate.now().plusDays(3));
        task.setSubject("Java");
        task.setPriority("LOW");
        Task savedTask = taskService.createTask(task);

        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Title");
        updatedTask.setSubject("Advanced Java");
        updatedTask.setPriority("HIGH");
        updatedTask.setDeadline(LocalDate.now().plusDays(1));
        updatedTask.setCompleted(true);

        Task result = taskService.updateTask(savedTask.getId(), updatedTask);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("HIGH", result.getPriority());
        assertTrue(result.isCompleted());
    }

    @Test
    void getTaskByIdShouldThrowExceptionForMissingTask() {
        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(999999L)
        );
    }
}

