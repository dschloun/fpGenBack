package be.unamur.fpgen.entity.view;

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
