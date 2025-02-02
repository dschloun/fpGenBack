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

/**
 * Repository for conversation messages.
 */
public interface ConversationMessageRepository {
    /**
     * Save a conversation message.
     * @param conversationMessage the conversation on which to save the message
     * @param conversationMessageList the conversation message listto save
     * @return the saved conversation message
     */
    List<ConversationMessage> saveConversationMessageList(Conversation conversation, List<ConversationMessage> conversationInstantMessageList);

    /**
     * delete a conversation message.
     * @param conversationInstantMessageId
     */
    void deleteConversationMessageById(Long conversationInstantMessageId);

    /**
     * Find conversations messages with filters
     * @param topic
     * @param type
     * @param content
     * @param startDate
     * @param endDate
     * @param pageable
     * @return a paginate result
     */
    ConversationMessagesPage findPagination(MessageTopicEnum topic, MessageTypeEnum type, String content, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable);

}
