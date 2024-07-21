package be.unamur.fpgen.repository;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.conversation.pagination.ConversationsPage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ConversationRepository {

    Conversation saveConversation(Conversation conversation);

    Conversation getConversationById(UUID conversationId);

    void deleteConversationById(UUID conversationId);

    ConversationsPage findPagination(MessageTopicEnum topic, MessageTypeEnum type, Integer maxInteractionNumber, Integer minInteractionNumber, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable);
}
