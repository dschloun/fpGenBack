package be.unamur.fpgen.repository.message;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.entity.conversation.ConversationEntity;
import be.unamur.fpgen.entity.instant_message.ConversationInstantMessageEntity;
import be.unamur.fpgen.mapper.domainToJpa.ConversationInstantMessageDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ConversationInstantMessageJpaToDomainMapper;
import be.unamur.fpgen.message.ConversationMessage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.message.pagination.conversation_message.ConversationMessagesPage;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.repository.ConversationMessageRepository;
import be.unamur.fpgen.repository.conversation.JpaConversationRepositoryCRUD;
import be.unamur.fpgen.repository.interclocutor.JpaInterlocutorRepositoryCRUD;
import be.unamur.fpgen.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * See the specifications in the {@link ConversationMessageRepository} interface.
 */
@Repository
public class JpaConversationMessageRepository implements ConversationMessageRepository {

    private final JpaConversationRepositoryCRUD jpaConversationRepositoryCRUD;
    private final JpaConversationMessageRepositoryCRUD jpaConversationMessageRepositoryCRUD;
    private final JpaInterlocutorRepositoryCRUD jpaInterlocutorRepositoryCRUD;

    public JpaConversationMessageRepository(JpaConversationRepositoryCRUD jpaConversationRepositoryCRUD,
                                            JpaConversationMessageRepositoryCRUD jpaConversationMessageRepositoryCRUD,
                                            JpaInterlocutorRepositoryCRUD jpaInterlocutorRepositoryCRUD) {
        this.jpaConversationRepositoryCRUD = jpaConversationRepositoryCRUD;
        this.jpaConversationMessageRepositoryCRUD = jpaConversationMessageRepositoryCRUD;
        this.jpaInterlocutorRepositoryCRUD = jpaInterlocutorRepositoryCRUD;
    }

    @Override
    public List<ConversationMessage> saveConversationMessageList(Conversation conversation, List<ConversationMessage> conversationInstantMessageList) {
        final ConversationEntity conversationEntity = jpaConversationRepositoryCRUD.getReferenceById(conversation.getId());

        final List<ConversationInstantMessageEntity> l = jpaConversationMessageRepositoryCRUD.saveAll(conversationInstantMessageList
                .stream()
                .map(i -> ConversationInstantMessageDomainToJpaMapper.mapForCreate(
                        i,
                        conversationEntity,
                        jpaInterlocutorRepositoryCRUD.getReferenceById(i.getSender().getId()),
                        jpaInterlocutorRepositoryCRUD.getReferenceById(i.getReceiver().getId())
                        )
                )
                .toList());

        return l.stream()
                .map(ConversationInstantMessageJpaToDomainMapper::map)
                .toList();
    }

    @Override
    public void deleteConversationMessageById(Long conversationInstantMessageId) {}

    @Override
    public ConversationMessagesPage findPagination(MessageTopicEnum topic, MessageTypeEnum type, String content, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable) {
        // 1. get in Page format
        Page<ConversationMessage> page = jpaConversationMessageRepositoryCRUD
                .findPagination(
                        topic,
                        type,
                        StringUtil.toLowerCaseIfNotNull(content),
                        startDate,
                        endDate,
                        pageable
                ).map(ConversationInstantMessageJpaToDomainMapper::map);

        // 2. convert
        final ConversationMessagesPage conversationMessagesPage = ConversationMessagesPage.newBuilder()
                .withPagination(new Pagination.Builder()
                        .size(page.getSize())
                        .totalSize((int) page.getTotalElements())
                        .page(page.getNumber())
                        .build())
                .withConversationMessageList(page.getContent())
                .build();

        // 3. return
        return conversationMessagesPage;
    }
}
