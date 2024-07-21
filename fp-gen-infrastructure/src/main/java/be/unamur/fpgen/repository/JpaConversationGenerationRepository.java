package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.generation.pagination.GenerationsPage;
import be.unamur.fpgen.instant_message.MessageTopicEnum;
import be.unamur.fpgen.instant_message.MessageTypeEnum;
import be.unamur.fpgen.mapper.domainToJpa.ConversationGenerationDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ConversationGenerationJpaToDomainMapper;
import be.unamur.fpgen.pagination.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaConversationGenerationRepository implements ConversationGenerationRepository{
    private final JpaConversationGenerationRepositoryCRUD jpaConversationGenerationRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;

    public JpaConversationGenerationRepository(JpaConversationGenerationRepositoryCRUD jpaConversationGenerationRepositoryCRUD, JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD) {
        this.jpaConversationGenerationRepositoryCRUD = jpaConversationGenerationRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
    }

    @Override
    public ConversationGeneration saveConversationGeneration(ConversationGeneration conversationGeneration) {
        return Optional.of(jpaConversationGenerationRepositoryCRUD.save(ConversationGenerationDomainToJpaMapper
                        .mapForCreate(conversationGeneration, jpaAuthorRepositoryCRUD.getReferenceById(conversationGeneration.getAuthor().getId()))))
                .map(ConversationGenerationJpaToDomainMapper::map)
                .orElseThrow();
    }

    @Override
    public Optional<ConversationGeneration> findConversationGenerationById(UUID conversationGenerationId) {
        return jpaConversationGenerationRepositoryCRUD.findById(conversationGenerationId).map(ConversationGenerationJpaToDomainMapper::map);
    }

    @Override
    public void deleteConversationGenerationById(UUID conversationGenerationId) {

    }

    @Override
    public GenerationsPage findPagination(MessageTypeEnum messageType, MessageTopicEnum messageTopic, String userPrompt, String systemPrompt, Integer quantity, String authorTrigram, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable) {

        // 1. get in Page format
        Page<ConversationGeneration> page = jpaConversationGenerationRepositoryCRUD.findPagination(
                messageTopic,
                messageType,
                authorTrigram,
                quantity,
                userPrompt,
                systemPrompt,
                startDate,
                endDate,
                pageable
        ).map(ConversationGenerationJpaToDomainMapper::map);

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
