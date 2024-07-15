package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.mapper.domainToJpa.InstantMessageGenerationDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InstantMessageGenerationJpaToDomainMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaInstantMessageInstantMessageGenerationRepository implements InstantMessageGenerationRepository {
    private final JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;

    public JpaInstantMessageInstantMessageGenerationRepository(JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD,
                                                               JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD) {
        this.jpaInstantMessageGenerationRepositoryCRUD = jpaInstantMessageGenerationRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
    }

    @Override
    public InstantMessageGeneration saveInstantMessageGeneration(InstantMessageGeneration abstractGeneration) {
            return Optional.of(jpaInstantMessageGenerationRepositoryCRUD.saveAndFlush(InstantMessageGenerationDomainToJpaMapper
                            .mapForCreate(abstractGeneration, jpaAuthorRepositoryCRUD.getReferenceById(abstractGeneration.getAuthor().getId()))))
                    .map(InstantMessageGenerationJpaToDomainMapper::mapInstantMessageGeneration)
                    .orElseThrow();
    }

    @Override
    public InstantMessageGeneration getInstantMessageGenerationById(UUID instantMessageGenerationId) {
        return null;
    }

    @Override
    public void deleteInstantMessageGenerationById(UUID instantMessageGenerationId) {

    }
}
