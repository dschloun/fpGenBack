package be.unamur.fpgen.repository;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.mapper.domainToJpa.ConversationDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ConversationJpaToDomainMapper;
import org.springframework.stereotype.Repository;

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

    }
}
