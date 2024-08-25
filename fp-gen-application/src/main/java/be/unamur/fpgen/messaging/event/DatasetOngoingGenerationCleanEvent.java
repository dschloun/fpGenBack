package be.unamur.fpgen.messaging.event;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

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
