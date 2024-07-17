package be.unamur.fpgen.repository;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.entity.instant_message.ConversationInstantMessageEntity;
import be.unamur.fpgen.instant_message.ConversationMessage;
import be.unamur.fpgen.mapper.domainToJpa.ConversationInstantMessageDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ConversationInstantMessageJpaToDomainMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaConversationMessageRepository implements ConversationMessageRepository{

    private final JpaConversationRepositoryCRUD jpaConversationRepositoryCRUD;
    private final JpaConversationMessageRepositoryCRUD jpaConversationMessageRepositoryCRUD;

    public JpaConversationMessageRepository(JpaConversationRepositoryCRUD jpaConversationRepositoryCRUD, JpaConversationMessageRepositoryCRUD jpaConversationMessageRepositoryCRUD) {
        this.jpaConversationRepositoryCRUD = jpaConversationRepositoryCRUD;
        this.jpaConversationMessageRepositoryCRUD = jpaConversationMessageRepositoryCRUD;
    }

    @Override
    public ConversationMessage saveConversationMessage(ConversationMessage conversationInstantMessage) {
        return null;
    }

    @Override
    public List<ConversationMessage> saveConversationMessageList(Conversation conversation, List<ConversationMessage> conversationInstantMessageList) {
        final ConversationEntity conversationEntity = jpaConversationRepositoryCRUD.getReferenceById(conversation.getId());

        final List<ConversationInstantMessageEntity> l = jpaConversationMessageRepositoryCRUD.saveAll(conversationInstantMessageList
                .stream()
                .map(i -> ConversationInstantMessageDomainToJpaMapper.mapForCreate(i, conversationEntity))
                .toList());

        return l.stream()
                .map(ConversationInstantMessageJpaToDomainMapper::map)
                .toList();
    }

    @Override
    public void deleteConversationMessageById(Long conversationInstantMessageId) {

    }
}
