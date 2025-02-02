package be.unamur.fpgen.repository;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.conversation.pagination.ConversationsPage;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.message.InstantMessage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repository for the Conversation.
 */
public interface ConversationRepository {

    /**
     * Save the conversation.
     *
     * @param conversation the conversation to save
     * @return the saved conversation
     */
    Conversation saveConversation(Conversation conversation);

    /**
     * Find conversation by its id
     * @param conversationId
     * @return the conversation
     */
    Conversation getConversationById(UUID conversationId);

    /**
     * Delete conversation by its id
     * @param conversationId
     */
    void deleteConversationById(UUID conversationId);

    /**
     * Find Conversation by differents filter criteria
     * @param topic
     * @param type
     * @param maxInteractionNumber
     * @param minInteractionNumber
     * @param startDate
     * @param endDate
     * @param pageable
     * @return a pageable of conversations
     */
    ConversationsPage findPagination(MessageTopicEnum topic, MessageTypeEnum type, Integer maxInteractionNumber, Integer minInteractionNumber, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable);

    /**
     * Save a list of conversations
     * @return the saved list of conversations
     */
    List<Conversation> saveConversationList(List<Conversation> conversationList, Generation generation);

    /**
     * Find all conversations by the generation id
     * @param generationId
     * @return a list of conversations
     */
    List<Conversation> findAllByGenerationId(UUID generationId);

    /**
     * Check if a conversation exists for a given hash
     * @param hash
     * @return a boolean
     */
    boolean existsByHash(String hash);
}
