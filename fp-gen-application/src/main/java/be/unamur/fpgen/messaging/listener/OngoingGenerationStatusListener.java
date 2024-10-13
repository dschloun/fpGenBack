package be.unamur.fpgen.messaging.listener;

import be.unamur.fpgen.messaging.event.OngoingGenerationStatusChangeEvent;
import be.unamur.fpgen.repository.OngoingGenerationRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OngoingGenerationStatusListener {
    private final OngoingGenerationRepository ongoingGenerationRepository;

    public OngoingGenerationStatusListener(OngoingGenerationRepository ongoingGenerationRepository) {
        this.ongoingGenerationRepository = ongoingGenerationRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateInstantMessages(final OngoingGenerationStatusChangeEvent event) {
         ongoingGenerationRepository.updateStatus(event.getOngoingGenerationId(), event.getStatus());
    }
}
