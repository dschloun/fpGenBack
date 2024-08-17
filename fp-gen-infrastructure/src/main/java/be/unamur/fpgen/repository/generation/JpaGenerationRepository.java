package be.unamur.fpgen.repository.generation;

import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.pagination.GenerationPage;
import be.unamur.fpgen.mapper.domainToJpa.GenerationDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ConversationGenerationJpaToDomainMapper;
import be.unamur.fpgen.mapper.jpaToDomain.GenerationJpaToDomainMapper;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.repository.GenerationRepository;
import be.unamur.fpgen.repository.author.JpaAuthorRepositoryCRUD;
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
    private final JpaGenerationRepositoryCRUD jpaGenerationRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;

    public JpaGenerationRepository(JpaGenerationRepositoryCRUD jpaGenerationRepositoryCRUD,
                                   JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD) {
        this.jpaGenerationRepositoryCRUD = jpaGenerationRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;

    }

    @Override
    public Generation saveGeneration(Generation generation) {
        return Optional.of(jpaGenerationRepositoryCRUD.save(GenerationDomainToJpaMapper
                        .mapForCreate(generation, jpaAuthorRepositoryCRUD.getReferenceById(generation.getAuthor().getId()))))
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
            String userPrompt,
            String systemPrompt,
            Integer quantity,
            String authorTrigram,
            OffsetDateTime startDate,
            OffsetDateTime endDate,
            List<UUID> notInDatasetIdList,
            List<UUID> inDatasetIdList,
            boolean isIn,
            Pageable pageable) {

        // 1. get in Page format
        final Page<Generation> page;

        if (GenerationTypeEnum.INSTANT_MESSAGE.equals(type)) {
            page = jpaGenerationRepositoryCRUD.findMessagePagination(
                    messageTopic,
                    messageType,
                    authorTrigram,
                    quantity,
                    StringUtil.toLowerCaseIfNotNull(userPrompt),
                    StringUtil.toLowerCaseIfNotNull(systemPrompt),
                    startDate,
                    endDate,
                    inDatasetIdList,
                    isIn,
                    pageable
            ).map(GenerationJpaToDomainMapper::map);

        } else if (Objects.isNull(inDatasetIdList)) {
            page = jpaGenerationRepositoryCRUD.findConversationPagination(
                    messageTopic,
                    messageType,
                    authorTrigram,
                    quantity,
                    StringUtil.toLowerCaseIfNotNull(userPrompt),
                    StringUtil.toLowerCaseIfNotNull(systemPrompt),
                    startDate,
                    endDate,
                    Objects.nonNull(notInDatasetIdList) ? notInDatasetIdList : List.of(UUID.fromString("00000000-0000-0000-0000-000000000000")),
                    isIn,
                    pageable
            ).map(ConversationGenerationJpaToDomainMapper::map);
        } else {
            throw new IllegalArgumentException("Either inDatasetIdList or notInDatasetIdList must be provided");
        }

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
}
