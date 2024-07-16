package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.AbstractGeneration;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.instant_message.InstantMessage;

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
}
