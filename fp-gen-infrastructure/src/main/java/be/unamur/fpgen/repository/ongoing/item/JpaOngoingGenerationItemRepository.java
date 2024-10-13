package be.unamur.fpgen.repository.ongoing.item;

import be.unamur.fpgen.repository.OngoingGenerationItemRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaOngoingGenerationItemRepository implements OngoingGenerationItemRepository {

    private final JpaOngoingGenerationItemRepositoryCRUD jpaOngoingGenerationItemRepositoryCRUD;

    public JpaOngoingGenerationItemRepository(JpaOngoingGenerationItemRepositoryCRUD jpaOngoingGenerationItemRepositoryCRUD) {
        this.jpaOngoingGenerationItemRepositoryCRUD = jpaOngoingGenerationItemRepositoryCRUD;
    }

    @Override
    public void deleteById(UUID id) {
        jpaOngoingGenerationItemRepositoryCRUD.deleteById(id);
    }
}
