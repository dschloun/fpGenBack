package be.unamur.fpgen.prompt.response.message;

public class Generation {
    private String messageType;
    private String messageTopic;
    private String message;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageTopic() {
        return messageTopic;
    }

    public void setMessageTopic(String messageTopic) {
        this.messageTopic = messageTopic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
