package be.unamur.fpgen.messaging.listener;

import be.unamur.fpgen.messaging.event.DatasetOngoingGenerationCleanEvent;
import be.unamur.fpgen.service.DatasetService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Listener that cleans the ongoing generation from the dataset after the generation transaction is committed.
 */
@Component
public class DatasetOngoingGenerationCleanListener {
    private final DatasetService datasetService;

    public DatasetOngoingGenerationCleanListener(final DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    /**
     * Cleans: removes the ongoing generation from the dataset after the generation transaction is committed.
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cleanOngoingGeneration(final DatasetOngoingGenerationCleanEvent event) {
        datasetService.removeOngoingGenerationFromDataset(event.getDatasetId());
    }
}
