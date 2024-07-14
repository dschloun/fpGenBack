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

    public JpaInstantMessageInstantMessageGenerationRepository(JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD) {
        this.jpaInstantMessageGenerationRepositoryCRUD = jpaInstantMessageGenerationRepositoryCRUD;
    }

    @Override
    public InstantMessageGeneration saveInstantMessageGeneration(InstantMessageGeneration abstractGeneration) {
        if(abstractGeneration.isInstantMessageGeneration()){
            return Optional.of(jpaInstantMessageGenerationRepositoryCRUD.saveAndFlush(InstantMessageGenerationDomainToJpaMapper.mapForCreate(abstractGeneration)))
                    .map(InstantMessageGenerationJpaToDomainMapper::mapInstantMessageGeneration)
                    .orElseThrow();
        }
        else{
            throw new IllegalArgumentException("Generation is not an instant message generation");
        }
    }

    @Override
    public InstantMessageGeneration getInstantMessageGenerationById(UUID instantMessageGenerationId) {
        return null;
    }

    @Override
    public void deleteInstantMessageGenerationById(UUID instantMessageGenerationId) {

    }
}
