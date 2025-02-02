package be.unamur.fpgen.repository.generation;

import be.unamur.fpgen.entity.view.GenerationProjectionJpaToDomainMapper;
import be.unamur.fpgen.repository.prompt.JpaPromptRepositoryCRUD;
import be.unamur.fpgen.repository.view.JpaGenerationProjectionRepositoryCRUD;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.pagination.GenerationPage;
import be.unamur.fpgen.mapper.domainToJpa.GenerationDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.GenerationJpaToDomainMapper;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.repository.GenerationRepository;
import be.unamur.fpgen.repository.author.JpaAuthorRepositoryCRUD;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.fpgen.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * See the specifications in the {@link GenerationRepository} interface.
 */
@Repository
public class JpaGenerationRepository implements GenerationRepository {
    private final JpaGenerationRepositoryCRUD jpaGenerationRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;
    private final JpaGenerationProjectionRepositoryCRUD jpaGenerationProjectionRepositoryCRUD;
    private final JpaPromptRepositoryCRUD jpaPromptRepositoryCRUD;

    public JpaGenerationRepository(JpaGenerationRepositoryCRUD jpaGenerationRepositoryCRUD,
                                   JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD,
                                   JpaGenerationProjectionRepositoryCRUD jpaGenerationProjectionRepositoryCRUD,
                                   JpaPromptRepositoryCRUD jpaPromptRepositoryCRUD) {
        this.jpaGenerationRepositoryCRUD = jpaGenerationRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
        this.jpaGenerationProjectionRepositoryCRUD = jpaGenerationProjectionRepositoryCRUD;
        this.jpaPromptRepositoryCRUD = jpaPromptRepositoryCRUD;
    }

    @Override
    public Generation saveGeneration(Generation generation) {
        return Optional.of(jpaGenerationRepositoryCRUD.save(GenerationDomainToJpaMapper
                        .mapForCreate(generation,
                                jpaAuthorRepositoryCRUD.getReferenceById(generation.getAuthor().getId()),
                                jpaPromptRepositoryCRUD.getReferenceById(generation.getPrompt().getId()))))
                .map(GenerationJpaToDomainMapper::map)
                .orElseThrow();
    }

    @Override
    public Optional<Generation> findGenerationById(UUID generationId) {
        return jpaGenerationRepositoryCRUD.findById(generationId)
                .map(GenerationJpaToDomainMapper::map);
    }

    @Override
    public void deleteGenerationById(UUID generationId) {
        jpaGenerationRepositoryCRUD.deleteById(generationId);
    }

    @Override
    public GenerationPage findPagination(
            GenerationTypeEnum type,
            MessageTypeEnum messageType,
            MessageTopicEnum messageTopic,
            Integer promptVersion,
            Integer quantity,
            String authorTrigram,
            OffsetDateTime startDate,
            OffsetDateTime endDate,
            List<UUID> datasetIdList,
            boolean isIn,
            Pageable pageable) {

        // 1. get in Page format
        final Page<Generation> page = jpaGenerationProjectionRepositoryCRUD.search(
                    convertGenerationTypeEnumToString(type),
                    Objects.nonNull(messageTopic) ? messageTopic.name() : null,
                    Objects.nonNull(messageType) ? messageType.name() : null,
                    StringUtil.toLowerCaseIfNotNull(authorTrigram),
                    quantity,
                    promptVersion,
                    startDate,
                    endDate,
                    MapperUtil.mapList(datasetIdList, UUID::toString),
                    isIn,
                    pageable
            ).map(GenerationProjectionJpaToDomainMapper::map);

        final GenerationPage generationPage = GenerationPage.newBuilder()
                .withPagination(new Pagination.Builder()
                        .size(page.getSize())
                        .totalSize((int) page.getTotalElements())
                        .page(page.getNumber())
                        .build())
                .withGenerationList(page.getContent())
                .build();

        // 3. return
        return generationPage;
    }

    private String convertGenerationTypeEnumToString(GenerationTypeEnum type) {
        return switch (type) {
            case INSTANT_MESSAGE -> "IMG";
            case CONVERSATION -> "CMG";
        };
    }

}
