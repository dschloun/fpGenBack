package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.Generation;
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
    public Generation saveInstantMessageGeneration(Generation generation) {
        if(generation.isInstantMessageGeneration()){
            return Optional.of(jpaInstantMessageGenerationRepositoryCRUD.saveAndFlush(GenerationDomainToJpaMapper.mapForCreate(generation)))
                    .map(GenerationJpaToDomainMapper::mapInstantMessageGeneration)
                    .orElseThrow();
        }
        else{
            throw new IllegalArgumentException("Generation is not an instant message generation");
        }
    }
}
