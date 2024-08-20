package be.unamur.fpgen.messaging.event;

import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.model.ConversationBatchCreation;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class ConversationOngoingGenerationEvent extends ApplicationEvent {
    private final GenerationTypeEnum type;
    private final UUID ongoingGenerationId;
    private final ConversationBatchCreation command;

    public GenerationTypeEnum getType() {
        return type;
    }

    public UUID getOngoingGenerationId() {
        return ongoingGenerationId;
    }

    public ConversationBatchCreation getCommand() {
        return command;
    }

    public ConversationOngoingGenerationEvent(Object source, GenerationTypeEnum type, UUID ongoingGenerationId, ConversationBatchCreation command) {
        super(source);
        this.type = type;
        this.ongoingGenerationId = ongoingGenerationId;
        this.command = command;
    }
}
