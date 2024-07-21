package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.generation.pagination.GenerationsPage;
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
    public Optional<InstantMessageGeneration> findInstantMessageGenerationById(UUID instantMessageGenerationId) {
        return jpaInstantMessageGenerationRepositoryCRUD.findById(instantMessageGenerationId)
                .map(InstantMessageGenerationJpaToDomainMapper::mapInstantMessageGeneration);
    }

    @Override
    public void deleteInstantMessageGenerationById(UUID instantMessageGenerationId) {

    }

    @Override
    public GenerationsPage findPagination(MessageTypeEnum messageType, MessageTopicEnum messageTopic, String userPrompt, String systemPrompt, Integer quantity, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable) {
        // 1. get in Page format
        Page<ConversationGeneration> page = jpaInstantMessageGenerationRepositoryCRUD.findPagination(
                messageTopic,
                messageType,
                authorTrigram,
                quantity,
                StringUtil.toLowerCaseIfNotNull(userPrompt),
                StringUtil.toLowerCaseIfNotNull(systemPrompt),
                startDate,
                endDate,
                pageable
        ).map(InstantMessageGenerationJpaToDomainMapper::map);

        final GenerationsPage generationsPage = GenerationsPage.newBuilder()
                .withPagination(new Pagination.Builder()
                        .size(page.getSize())
                        .totalSize((int) page.getTotalElements())
                        .page(page.getNumber())
                        .build())
                .withGenerationList(page.getContent())
                .build();

        // 3. return
        return generationsPage;
    }
}
