package be.unamur.fpgen.messaging.event;

import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class OngoingGenerationStatusChangeEvent extends ApplicationEvent {
    private final UUID ongoingGenerationId;
    private final OngoingGenerationStatus status;

    public UUID getOngoingGenerationId() {
        return ongoingGenerationId;
    }

    public OngoingGenerationStatus getStatus() {
        return status;
    }

    public OngoingGenerationStatusChangeEvent(Object source, UUID ongoingGenerationId, OngoingGenerationStatus status) {
        super(source);
        this.ongoingGenerationId = ongoingGenerationId;
        this.status = status;
    }
}
