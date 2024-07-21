package be.unamur.fpgen.repository;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.conversation.pagination.ConversationsPage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.mapper.domainToJpa.ConversationDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ConversationJpaToDomainMapper;
import be.unamur.fpgen.pagination.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public class JpaConversationRepository implements ConversationRepository {
    private final JpaConversationGenerationRepositoryCRUD jpaConversationGenerationRepositoryCRUD;
    private final JpaConversationRepositoryCRUD jpaConversationRepositoryCRUD;

    public JpaConversationRepository(JpaConversationGenerationRepositoryCRUD jpaConversationGenerationRepositoryCRUD, JpaConversationRepositoryCRUD jpaConversationRepositoryCRUD) {
        this.jpaConversationGenerationRepositoryCRUD = jpaConversationGenerationRepositoryCRUD;
        this.jpaConversationRepositoryCRUD = jpaConversationRepositoryCRUD;
    }

    @Override
    public Conversation saveConversation(Conversation conversation) {
        return ConversationJpaToDomainMapper.map(
                jpaConversationRepositoryCRUD.save(
                        ConversationDomainToJpaMapper.mapForCreate(
                                conversation,
                                jpaConversationGenerationRepositoryCRUD.getReferenceById(conversation.getGenerationId()))));
    }

    @Override
    public Conversation getConversationById(UUID conversationId) {
        return ConversationJpaToDomainMapper.map(
                jpaConversationRepositoryCRUD.getReferenceById(conversationId)
        );
    }

    @Override
    public void deleteConversationById(UUID conversationId) {
        jpaConversationRepositoryCRUD.deleteById(conversationId);
    }

    @Override
    public ConversationsPage findPagination(MessageTopicEnum topic, MessageTypeEnum type, Integer maxInteractionNumber, Integer minInteractionNumber, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable) {
        // 1. get in Page format
        Page<Conversation> page = jpaConversationRepositoryCRUD.findPagination(
                topic,
                type,
                minInteractionNumber,
                maxInteractionNumber,
                startDate,
                endDate,
                pageable
        ).map(ConversationJpaToDomainMapper::map);

        // 2. convert
        final ConversationsPage conversationsPage = ConversationsPage.newBuilder()
                .withPagination(new Pagination.Builder()
                        .size(page.getSize())
                        .totalSize((int) page.getTotalElements())
                        .page(page.getNumber())
                        .build())
                .withConversationList(page.getContent())
                .build();

        // 3. return
        return conversationsPage;

    }
}
