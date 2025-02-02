package be.unamur.fpgen.repository.ongoing;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.generation.ongoing_generation.OngoingGenerationEntity;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGeneration;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationItem;
import be.unamur.fpgen.generation.ongoing_generation.OngoingGenerationStatus;
import be.unamur.fpgen.mapper.domainToJpa.OngoingGenerationDomainToJpaMapper;
import be.unamur.fpgen.mapper.domainToJpa.OngoingGenerationItemDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.OngoingGenerationJpaToDomainMapper;
import be.unamur.fpgen.repository.OngoingGenerationItemRepository;
import be.unamur.fpgen.repository.author.JpaAuthorRepositoryCRUD;
import be.unamur.fpgen.repository.OngoingGenerationRepository;
import be.unamur.fpgen.utils.MapperUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * See the specifications in the {@link OngoingGenerationRepository} interface.
 */
@Repository
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
        final AuthorEntity authorEntity = jpaAuthorRepositoryCRUD.getReferenceById(ongoingGeneration.getAuthor().getId());
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
    public void updateStatus(UUID id, OngoingGenerationStatus status) {
        final OngoingGenerationEntity ongoingGenerationEntity = jpaOngoingGenerationRepositoryCRUD.findById(id).orElseThrow();
        ongoingGenerationEntity.setStatus(status);
        jpaOngoingGenerationRepositoryCRUD.save(ongoingGenerationEntity);
    }

    @Override
    public List<OngoingGeneration> findAllByStatus(OngoingGenerationStatus status) {
        return MapperUtil.mapList(jpaOngoingGenerationRepositoryCRUD.findAllByStatus(status), OngoingGenerationJpaToDomainMapper::map);
    }

    @Override
    public void deleteById(UUID id) {
        jpaOngoingGenerationRepositoryCRUD.deleteById(id);
    }
}
