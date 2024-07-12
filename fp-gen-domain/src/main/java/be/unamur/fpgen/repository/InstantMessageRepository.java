package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.instant_message.InstantMessage;

import java.util.List;

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
    List<InstantMessage> saveInstantMessageList(List<InstantMessage> instantMessage, Generation generation);
}
