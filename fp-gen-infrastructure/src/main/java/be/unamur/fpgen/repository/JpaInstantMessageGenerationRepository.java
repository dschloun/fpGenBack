package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.mapper.domainToJpa.InstantMessageGenerationDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InstantMessageGenerationJpaToDomainMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaInstantMessageGenerationRepository implements InstantMessageGenerationRepository {
    private final JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;

    public JpaInstantMessageGenerationRepository(JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD,
                                                 JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD) {
        this.jpaInstantMessageGenerationRepositoryCRUD = jpaInstantMessageGenerationRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
    }

    @Override
    public InstantMessageGeneration saveInstantMessageGeneration(InstantMessageGeneration generation) {
            return Optional.of(jpaInstantMessageGenerationRepositoryCRUD.save(InstantMessageGenerationDomainToJpaMapper
                            .mapForCreate(generation, jpaAuthorRepositoryCRUD.getReferenceById(generation.getAuthor().getId()))))
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
