package be.unamur.fpgen.messaging.event;

import be.unamur.fpgen.dataset.DatasetTypeEnum;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

/**
 * Event that is published when the computation of statistics for a dataset is requested.
 */
public class StatisticComputationEvent extends ApplicationEvent {
    private final UUID datasetId;
    private final DatasetTypeEnum datasetType;

    public UUID getDatasetId() {
        return datasetId;
    }

    public DatasetTypeEnum getDatasetType() {
        return datasetType;
    }

    public StatisticComputationEvent(Object source, UUID datasetId, DatasetTypeEnum datasetType) {
        super(source);
        this.datasetId = datasetId;
        this.datasetType = datasetType;
    }
}
