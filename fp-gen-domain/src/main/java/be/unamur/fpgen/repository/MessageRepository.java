package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.message.InstantMessage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.message.pagination.InstantMessage.InstantMessagesPage;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @overview
 * The MessageRepository interface provides a contract for InstantMessage,
 * defining methods to manage InstantMessage.
 */
public interface MessageRepository {

    /**
     * Save a InstantMessage.
     * @requires message != null
     * @return the saved InstantMessage
     */
    List<InstantMessage> saveInstantMessageList(List<InstantMessage> instantMessageList, Generation generation);

    /**
     * Delete a InstantMessage by id.
     * @requires instantMessageId != null
     */
    void deleteInstantMessageById(UUID instantMessageId);

    /**
     * Get a InstantMessage by id.
     * @param instantMessageId
     * @return the InstantMessage empty if not found
     */
    Optional<InstantMessage> getInstantMessageById(UUID instantMessageId);

    /**
     * find InstantMessages which match the given filters
     * @param topic
     * @param type
     * @param content
     * @param startDate
     * @param endDate
     * @param pageable
     * @return a pageable of InstantMessages
     */
    InstantMessagesPage findPagination(MessageTopicEnum topic, MessageTypeEnum type, String content, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable);

    /**
     * find InstantMessages by generationId
     * @param generationId
     * @return a list of InstantMessages
     */
    List<InstantMessage> findInstantMessageByGenerationId(UUID generationId);

    /**
     * Check if a InstantMessage exists by hash (unicity check)
     * @param hash
     * @return true if exists, false otherwise
     */
    boolean existByHash(String hash);
}
