package be.unamur.fpgen.messaging.event;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

/**
 * This class is an event used to delete the ongoing generation once they have been generated
 */
public class DatasetOngoingGenerationCleanEvent extends ApplicationEvent {
    private final UUID datasetId;

    public UUID getDatasetId() {
        return datasetId;
    }

    public DatasetOngoingGenerationCleanEvent(final Object source, final UUID datasetId) {
        super(source);
        this.datasetId = datasetId;
    }
}
