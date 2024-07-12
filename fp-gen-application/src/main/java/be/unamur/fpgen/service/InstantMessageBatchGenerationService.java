package be.unamur.fpgen.service;

import be.unamur.fpgen.repository.InstantMessageRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class InstantMessageBatchGenerationService {

    private final InstantMessageRepository instantMessageRepository;

    public InstantMessageBatchGenerationService(InstantMessageRepository instantMessageRepository) {
        this.instantMessageRepository = instantMessageRepository;
    }

    @Transactional
    public void generateInstantMessages() {
        // implementation
    }


}
