package be.unamur.fpgen.repository;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.message.ConversationMessage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.message.pagination.InstantMessage.InstantMessagesPage;
import be.unamur.fpgen.message.pagination.conversation_message.ConversationMessagesPage;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;

public interface ConversationMessageRepository {
    List<ConversationMessage> saveConversationMessageList(Conversation conversation, List<ConversationMessage> conversationInstantMessageList);

    void deleteConversationMessageById(Long conversationInstantMessageId);

    ConversationMessagesPage findPagination(MessageTopicEnum topic, MessageTypeEnum type, String content, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable);

}
