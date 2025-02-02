package be.unamur.fpgen.repository.conversation;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.conversation.pagination.ConversationsPage;
import be.unamur.fpgen.entity.generation.ConversationGenerationEntity;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.mapper.domainToJpa.ConversationDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ConversationJpaToDomainMapper;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.repository.ConversationMessageRepository;
import be.unamur.fpgen.repository.ConversationRepository;
import be.unamur.fpgen.repository.generation.JpaConversationGenerationRepositoryCRUD;
import be.unamur.fpgen.utils.MapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * See the specifications in the {@link ConversationRepository} interface.
 */
@Repository
public class JpaConversationRepository implements ConversationRepository {
    private final JpaConversationRepositoryCRUD jpaConversationRepositoryCRUD;
    private final JpaConversationGenerationRepositoryCRUD jpaConversationGenerationRepositoryCRUD;

    public JpaConversationRepository(JpaConversationRepositoryCRUD jpaConversationRepositoryCRUD, JpaConversationGenerationRepositoryCRUD jpaConversationGenerationRepositoryCRUD) {
        this.jpaConversationRepositoryCRUD = jpaConversationRepositoryCRUD;
        this.jpaConversationGenerationRepositoryCRUD = jpaConversationGenerationRepositoryCRUD;
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

    @Override
    public List<Conversation> saveConversationList(List<Conversation> conversationList, Generation generation) {
        final ConversationGenerationEntity generationEntity = jpaConversationGenerationRepositoryCRUD.findById(generation.getId()).orElseThrow();
        List<Conversation> l = jpaConversationRepositoryCRUD.saveAll(
                conversationList.stream()
                        .map(c -> ConversationDomainToJpaMapper.mapForCreate(c, generationEntity))
                        .toList()
        ).stream()
                .map(ConversationJpaToDomainMapper::map)
                .toList();

        return l;
    }

    @Override
    public List<Conversation> findAllByGenerationId(UUID generationId) {
        return MapperUtil.mapList(jpaConversationRepositoryCRUD.findAllByConversationGenerationId(generationId), ConversationJpaToDomainMapper::map);
    }

    @Override
    public boolean existsByHash(String hash) {
        return jpaConversationRepositoryCRUD.existsByHash(hash);
    }
}
