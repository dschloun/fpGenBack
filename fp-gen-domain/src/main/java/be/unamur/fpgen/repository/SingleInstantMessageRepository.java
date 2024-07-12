package be.unamur.fpgen.repository;

import be.unamur.fpgen.instant_message.SingleInstantMessage;

/**
 * @overview
 * The SingleInstantMessageRepository interface provides a contract for SingleInstantMessage,
 * defining methods to manage SingleInstantMessage.
 */
public interface SingleInstantMessageRepository {

    /**
     * Save a SingleInstantMessage.
     * @requires message != null
     * @return the saved SingleInstantMessage
     */
    SingleInstantMessage saveSingleInstantMessage(SingleInstantMessage singleInstantMessage);
}
