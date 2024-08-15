package be.unamur.fpgen.messaging.event;

import be.unamur.model.InstantMessageBatchCreation;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class OngoingGenerationEvent extends ApplicationEvent {
    private final UUID ongoingGenerationId;
    private final InstantMessageBatchCreation command;

    public UUID getOngoingGenerationId() {
        return ongoingGenerationId;
    }

    public InstantMessageBatchCreation getCommand() {
        return command;
    }

    public OngoingGenerationEvent(Object source, UUID ongoingGenerationId, InstantMessageBatchCreation command) {
        super(source);
        this.ongoingGenerationId = ongoingGenerationId;
        this.command = command;
    }
}
