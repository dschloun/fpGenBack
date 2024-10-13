package be.unamur.fpgen.repository.ongoing.item;

import be.unamur.fpgen.repository.OngoingGenerationItemRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaOngoingGenerationItemRepository implements OngoingGenerationItemRepository {

    private final OngoingGenerationItemRepository ongoingGenerationItemRepository;

    public JpaOngoingGenerationItemRepository(OngoingGenerationItemRepository ongoingGenerationItemRepository) {
        this.ongoingGenerationItemRepository = ongoingGenerationItemRepository;
    }

    @Override
    public void deleteById(UUID id) {
        ongoingGenerationItemRepository.deleteById(id);
    }
}
