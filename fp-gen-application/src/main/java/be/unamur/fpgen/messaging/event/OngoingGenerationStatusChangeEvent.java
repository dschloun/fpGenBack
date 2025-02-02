package be.unamur.fpgen.messaging.event;

import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;
import org.springframework.context.ApplicationEvent;

import java.util.List;
import java.util.UUID;

/**
 * Event that is published when the status of an ongoing generation changes.
 */
public class OngoingGenerationStatusChangeEvent extends ApplicationEvent {
    private final UUID ongoingGenerationId;
    private final OngoingGenerationStatus status;
    private final List<UUID> itemsToDelete;

    public UUID getOngoingGenerationId() {
        return ongoingGenerationId;
    }

    public OngoingGenerationStatus getStatus() {
        return status;
    }

    public List<UUID> getItemsToDelete() {
        return itemsToDelete;
    }

    public OngoingGenerationStatusChangeEvent(Object source, UUID ongoingGenerationId, OngoingGenerationStatus status, List<UUID> itemsToDelete) {
        super(source);
        this.ongoingGenerationId = ongoingGenerationId;
        this.status = status;
        this.itemsToDelete = itemsToDelete;
    }
}
