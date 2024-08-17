package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.generation.pagination.InstantMessageGenerationsPage;
import be.unamur.fpgen.mapper.domainToJpa.InstantMessageGenerationDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InstantMessageGenerationJpaToDomainMapper;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
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
public class JpaGenerationRepository implements GenerationRepository {
    private final JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;


    public JpaGenerationRepository(JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD,
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
    public Optional<InstantMessageGeneration> findInstantMessageGenerationById(UUID instantMessageGenerationId) {
        return jpaInstantMessageGenerationRepositoryCRUD.findById(instantMessageGenerationId)
                .map(InstantMessageGenerationJpaToDomainMapper::mapInstantMessageGeneration);
    }

    @Override
    public void deleteInstantMessageGenerationById(UUID instantMessageGenerationId) {
        jpaInstantMessageGenerationRepositoryCRUD.deleteById(instantMessageGenerationId);
    }

    @Override
    public InstantMessageGenerationsPage findPagination(
            MessageTypeEnum messageType,
            MessageTopicEnum messageTopic,
            String userPrompt,
            String systemPrompt,
            Integer quantity,
            String authorTrigram,
            OffsetDateTime startDate,
            OffsetDateTime endDate,
            List<UUID> notInDatasetIdList,
            List<UUID> inDatasetIdList,
            Pageable pageable) {
        // 1. get in Page format
        final Page<InstantMessageGeneration> page;

        if (Objects.nonNull(inDatasetIdList) && !inDatasetIdList.isEmpty() && Objects.isNull(notInDatasetIdList)) {
            page = jpaInstantMessageGenerationRepositoryCRUD.findPaginationIn(
                    messageTopic,
                    messageType,
                    authorTrigram,
                    quantity,
                    StringUtil.toLowerCaseIfNotNull(userPrompt),
                    StringUtil.toLowerCaseIfNotNull(systemPrompt),
                    startDate,
                    endDate,
                    inDatasetIdList,
                    pageable
            ).map(InstantMessageGenerationJpaToDomainMapper::map);
        } else if (Objects.isNull(inDatasetIdList)) {
            page = jpaInstantMessageGenerationRepositoryCRUD.findPaginationNotIn(
                    messageTopic,
                    messageType,
                    authorTrigram,
                    quantity,
                    StringUtil.toLowerCaseIfNotNull(userPrompt),
                    StringUtil.toLowerCaseIfNotNull(systemPrompt),
                    startDate,
                    endDate,
                    Objects.nonNull(notInDatasetIdList) ? notInDatasetIdList : List.of(UUID.fromString("00000000-0000-0000-0000-000000000000")),
                    pageable
            ).map(InstantMessageGenerationJpaToDomainMapper::map);
        } else {
            throw new IllegalArgumentException("Either inDatasetIdList or notInDatasetIdList must be provided");
        }

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
