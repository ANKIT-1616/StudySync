package com.studysync.controller;

import com.studysync.model.Task;
import com.studysync.service.StudyPlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/study-plan")
public class StudyPlanController {

    private final StudyPlanService studyPlanService;

    public StudyPlanController(StudyPlanService studyPlanService) {
        this.studyPlanService = studyPlanService;
    }

    @GetMapping("/rebalance")
    public List<Task> getRebalancedPlan() {
        return studyPlanService.generateRebalancedPlan();
    }
}