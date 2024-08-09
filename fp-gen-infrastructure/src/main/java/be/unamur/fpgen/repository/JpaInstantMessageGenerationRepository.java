package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.view.GenerationProjection;
import be.unamur.fpgen.entity.view.JpaGenerationProjectionRepositoryCRUD;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.generation.pagination.ConversationGenerationsPage;
import be.unamur.fpgen.generation.pagination.InstantMessageGenerationsPage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.mapper.domainToJpa.InstantMessageGenerationDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InstantMessageGenerationJpaToDomainMapper;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaInstantMessageGenerationRepository implements InstantMessageGenerationRepository {
    private final JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;
    private final JpaGenerationProjectionRepositoryCRUD jpaGenerationProjectionRepositoryCRUD;

    public JpaInstantMessageGenerationRepository(JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD,
                                                 JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD, JpaGenerationProjectionRepositoryCRUD jpaGenerationProjectionRepositoryCRUD) {
        this.jpaInstantMessageGenerationRepositoryCRUD = jpaInstantMessageGenerationRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
        this.jpaGenerationProjectionRepositoryCRUD = jpaGenerationProjectionRepositoryCRUD;
    }

    @Override
    public InstantMessageGeneration saveInstantMessageGeneration(InstantMessageGeneration generation) {
            return Optional.of(jpaInstantMessageGenerationRepositoryCRUD.save(InstantMessageGenerationDomainToJpaMapper
                            .mapForCreate(generation, jpaAuthorRepositoryCRUD.getReferenceById(generation.getAuthor().getId()))))
                    .map(InstantMessageGenerationJpaToDomainMapper::mapInstantMessageGeneration)
                    .orElseThrow();
    }

    @Override
    public Optional<InstantMessageGeneration> findInstantMessageGenerationById(UUID instantMessageGenerationId) {
        return jpaInstantMessageGenerationRepositoryCRUD.findById(instantMessageGenerationId)
                .map(InstantMessageGenerationJpaToDomainMapper::mapInstantMessageGeneration);
    }

    @Override
    public void deleteInstantMessageGenerationById(UUID instantMessageGenerationId) {
        jpaInstantMessageGenerationRepositoryCRUD.deleteById(instantMessageGenerationId);
    }

    @Override
    public InstantMessageGenerationsPage findPagination(MessageTypeEnum messageType, MessageTopicEnum messageTopic, String userPrompt, String systemPrompt, Integer quantity, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, List<UUID> datasetIdList, Pageable pageable) {
        // 1. get in Page format
        Page<GenerationProjection> projectionPage = jpaGenerationProjectionRepositoryCRUD.search(
                Objects.nonNull(messageTopic) ? messageTopic.name() : null,
                Objects.nonNull(messageType) ? messageType.name() : null,
                authorTrigram,
                quantity,
                StringUtil.toLowerCaseIfNotNull(userPrompt),
                startDate,
                endDate,
                Objects.nonNull(datasetIdList) ? datasetIdList : List.of(UUID.fromString("00000000-0000-0000-0000-000000000000")),
                pageable
        );
        Page<InstantMessageGeneration> page = jpaInstantMessageGenerationRepositoryCRUD.findPagination(
                messageTopic,
                messageType,
                authorTrigram,
                quantity,
                StringUtil.toLowerCaseIfNotNull(userPrompt),
                StringUtil.toLowerCaseIfNotNull(systemPrompt),
                startDate,
                endDate,
                Objects.nonNull(datasetIdList) ? datasetIdList : List.of(UUID.fromString("00000000-0000-0000-0000-000000000000")),
                pageable
        ).map(InstantMessageGenerationJpaToDomainMapper::map);

        final InstantMessageGenerationsPage instantMessageGenerationsPage = InstantMessageGenerationsPage.newBuilder()
                .withPagination(new Pagination.Builder()
                        .size(page.getSize())
                        .totalSize((int) page.getTotalElements())
                        .page(page.getNumber())
                        .build())
                .withGenerationList(page.getContent())
                .build();

        // 3. return
        return instantMessageGenerationsPage;
    }
}
