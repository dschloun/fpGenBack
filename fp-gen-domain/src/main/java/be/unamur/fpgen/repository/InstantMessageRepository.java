package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.InstantMessageGeneration;
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
 * The SingleInstantMessageRepository interface provides a contract for SingleInstantMessage,
 * defining methods to manage SingleInstantMessage.
 */
public interface InstantMessageRepository {

    /**
     * Save a SingleInstantMessage.
     * @requires message != null
     * @return the saved SingleInstantMessage
     */
    List<InstantMessage> saveInstantMessageList(List<InstantMessage> instantMessageList, InstantMessageGeneration instantMessageGeneration);

    /**
     * Delete a SingleInstantMessage by id.
     * @requires instantMessageId != null
     */
    void deleteInstantMessageById(UUID instantMessageId);

    Optional<InstantMessage> getInstantMessageById(UUID instantMessageId);

    InstantMessagesPage findPagination(MessageTopicEnum topic, MessageTypeEnum type, String content, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable);

    List<InstantMessage> findInstantMessageByGenerationId(UUID generationId);
}
