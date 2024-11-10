package be.unamur.fpgen.prompt.response.conversation;

public class ConversationMessage {
    private String messageOrder;
    private String actorType;
    private String content;

    public String getMessageOrder() {
        return messageOrder;
    }

    public void setMessageOrder(String messageOrder) {
        this.messageOrder = messageOrder;
    }

    public String getActorType() {
        return actorType;
    }

    public void setActorType(String actorType) {
        this.actorType = actorType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
