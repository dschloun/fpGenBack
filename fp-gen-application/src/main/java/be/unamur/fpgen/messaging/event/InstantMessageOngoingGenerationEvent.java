package be.unamur.fpgen.messaging.event;

import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.model.InstantMessageBatchCreation;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class InstantMessageOngoingGenerationEvent extends ApplicationEvent {
    private final GenerationTypeEnum type;
    private final UUID ongoingGenerationId;
    private final InstantMessageBatchCreation command;

    public GenerationTypeEnum getType() {
        return type;
    }

    public UUID getOngoingGenerationId() {
        return ongoingGenerationId;
    }

    public InstantMessageBatchCreation getCommand() {
        return command;
    }

    public InstantMessageOngoingGenerationEvent(Object source, GenerationTypeEnum type, UUID ongoingGenerationId, InstantMessageBatchCreation command) {
        super(source);
        this.type = type;
        this.ongoingGenerationId = ongoingGenerationId;
        this.command = command;
    }
}
