package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.AbstractGeneration;
import be.unamur.fpgen.mapper.domainToJpa.GenerationDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.GenerationJpaToDomainMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaInstantMessageGenerationRepository implements GenerationRepository {
    private final JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD;

    public JpaInstantMessageGenerationRepository(JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD) {
        this.jpaInstantMessageGenerationRepositoryCRUD = jpaInstantMessageGenerationRepositoryCRUD;
    }

    @Override
    public AbstractGeneration saveInstantMessageGeneration(AbstractGeneration abstractGeneration) {
        if(abstractGeneration.isInstantMessageGeneration()){
            return Optional.of(jpaInstantMessageGenerationRepositoryCRUD.saveAndFlush(GenerationDomainToJpaMapper.mapForCreate(abstractGeneration)))
                    .map(GenerationJpaToDomainMapper::mapInstantMessageGeneration)
                    .orElseThrow();
        }
        else{
            throw new IllegalArgumentException("Generation is not an instant message generation");
        }
    }
}
