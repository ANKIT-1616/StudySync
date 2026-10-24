
package com.studysync.service;

import com.studysync.model.Task;
import java.time.LocalDate;
import java.util.List;


public record StudyPlanProposal(
        LocalDate generatedOn,
        String reason,
        int unfinishedTaskCount,
        List<Task> proposedTasks
) {
    public StudyPlanProposal {
        proposedTasks = List.copyOf(proposedTasks);
    }
}

