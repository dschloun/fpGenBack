package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.instant_message.InstantMessage;
import be.unamur.fpgen.mapper.domainToJpa.InstantMessageDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InstantMessageJpaToDomainMapper;
import liquibase.repackaged.org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaInstantMessageRepository implements InstantMessageRepository {

    private final JpaInstantMessageRepositoryCRUD jpaInstantMessageRepositoryCRUD;
    private final JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD;

    public JpaInstantMessageRepository(final JpaInstantMessageRepositoryCRUD jpaInstantMessageRepositoryCRUD,
                                       final JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD) {
        this.jpaInstantMessageRepositoryCRUD = jpaInstantMessageRepositoryCRUD;
        this.jpaInstantMessageGenerationRepositoryCRUD = jpaInstantMessageGenerationRepositoryCRUD;
    }

    @Override
    public List<InstantMessage> saveInstantMessageList(List<InstantMessage> instantMessageList, Generation generation) {
        InstantMessageGenerationEntity generationEntity = jpaInstantMessageGenerationRepositoryCRUD.getReferenceById(generation.getId());
        List<InstantMessageEntity> l = jpaInstantMessageRepositoryCRUD.saveAll(ListUtils.emptyIfNull(instantMessageList)
                .stream()
                .map(i -> InstantMessageDomainToJpaMapper.mapForCreate(i, generationEntity))
                .toList());

        return l.stream()
                .map(InstantMessageJpaToDomainMapper::map)
                .toList();
    }
}
