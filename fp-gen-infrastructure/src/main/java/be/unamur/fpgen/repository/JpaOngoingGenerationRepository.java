package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationEntity;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItem;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;
import be.unamur.fpgen.mapper.domainToJpa.OngoingGenerationDomainToJpaMapper;
import be.unamur.fpgen.mapper.domainToJpa.OngoingGenerationItemDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.OngoingGenerationJpaToDomainMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JpaOngoingGenerationRepository implements OngoingGenerationRepository {

    private final JpaOngoingGenerationRepositoryCRUD jpaOngoingGenerationRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;

    public JpaOngoingGenerationRepository(JpaOngoingGenerationRepositoryCRUD jpaOngoingGenerationRepositoryCRUD,
                                          JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD) {
        this.jpaOngoingGenerationRepositoryCRUD = jpaOngoingGenerationRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
    }

    @Override
    public OngoingGeneration save(OngoingGeneration ongoingGeneration) {
        final AuthorEntity authorEntity = jpaAuthorRepositoryCRUD.findById(ongoingGeneration.getAuthor().getId()).orElseThrow();
        return OngoingGenerationJpaToDomainMapper.map(
                jpaOngoingGenerationRepositoryCRUD.save(OngoingGenerationDomainToJpaMapper.mapForCreate(ongoingGeneration, authorEntity))
        );
    }

    @Override
    public Optional<OngoingGeneration> findById(UUID id) {
        return jpaOngoingGenerationRepositoryCRUD.findById(id).map(OngoingGenerationJpaToDomainMapper::map);
    }

    @Override
    public void addItemList(OngoingGeneration ongoingGeneration, List<OngoingGenerationItem> itemList) {
        final OngoingGenerationEntity ongoingGenerationEntity = jpaOngoingGenerationRepositoryCRUD.findById(ongoingGeneration.getId()).orElseThrow();
        itemList.forEach(item -> ongoingGenerationEntity.getItemList().add(OngoingGenerationItemDomainToJpaMapper.mapForCreate(item, ongoingGenerationEntity)));
        jpaOngoingGenerationRepositoryCRUD.save(ongoingGenerationEntity);
    }

    @Override
    public List<OngoingGeneration> findAllByAuthorId(UUID authorId) {
        return null;
    }

    @Override
    public List<OngoingGeneration> findAllByAuthorIdAndStatusList(UUID authorId, List<OngoingGenerationStatus> statusList) {
        return null;
    }
}
