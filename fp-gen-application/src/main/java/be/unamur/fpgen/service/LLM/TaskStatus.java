package be.unamur.fpgen.service.LLM;

import org.springframework.stereotype.Component;

@Component
public class TaskStatus {
    // inform the cron than a generation is ongoing in order not to launch a second before it finished
    private boolean running = false;

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }
}
