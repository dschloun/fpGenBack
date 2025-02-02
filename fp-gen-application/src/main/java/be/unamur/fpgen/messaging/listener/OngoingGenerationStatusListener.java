package be.unamur.fpgen.messaging.listener;

import be.unamur.fpgen.messaging.event.OngoingGenerationStatusChangeEvent;
import be.unamur.fpgen.repository.OngoingGenerationItemRepository;
import be.unamur.fpgen.repository.OngoingGenerationRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Listener for {@link OngoingGenerationStatusChangeEvent} that updates the status of the ongoing generation and deletes
 * the items that are no longer needed.
 */
@Component
public class OngoingGenerationStatusListener {
    private final OngoingGenerationRepository ongoingGenerationRepository;
    private final OngoingGenerationItemRepository ongoingGenerationItemRepository;

    public OngoingGenerationStatusListener(OngoingGenerationRepository ongoingGenerationRepository, OngoingGenerationItemRepository ongoingGenerationItemRepository) {
        this.ongoingGenerationRepository = ongoingGenerationRepository;
        this.ongoingGenerationItemRepository = ongoingGenerationItemRepository;
    }

    /**
     * Updates the status of the ongoing generation and deletes the items that are no longer needed.
     *
     * @param event the event to handle
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(final OngoingGenerationStatusChangeEvent event) {
        ongoingGenerationRepository.updateStatus(event.getOngoingGenerationId(), event.getStatus());

        if (!event.getItemsToDelete().isEmpty()) {
            ongoingGenerationItemRepository.deleteAllByIdIn(event.getItemsToDelete());
        }
    }
}
