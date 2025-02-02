package be.unamur.fpgen.service.LLM;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Service to execute a task at a fixed rate using cron expression.

 */
@Service
public class CronTaskService {
    private final TaskStatus taskStatus;
    private final LLMGenerationService llmGenerationService;

    public CronTaskService(TaskStatus taskStatus, LLMGenerationService llmGenerationService) {
        this.taskStatus = taskStatus;
        this.llmGenerationService = llmGenerationService;
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void executeTask() {
        if (taskStatus.isRunning()) {
            System.out.println("Task already running, skipping execution");
            return;
        }

        taskStatus.setRunning(true);
        try {
            // Execute the expensive generation task
            llmGenerationService.generate();
        } finally {
            taskStatus.setRunning(false); // reinitialize the task status
        }
    }
}
