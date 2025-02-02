package be.unamur.fpgen.entity.view;

/**
 * This interface is used to define the projection of the conversation message entity that will be used to download the conversation messages.
 */
public interface ConversationMessageDownloadProjection {
    String getId();
    String getConversationId();
    Integer getOrderNumber();
    String getType();
    String getTopic();
    String getContent();
    Integer getSenderId();
    Integer getReceiverId();
}
