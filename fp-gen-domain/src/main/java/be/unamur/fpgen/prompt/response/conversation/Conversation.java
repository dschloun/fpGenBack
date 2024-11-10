package be.unamur.fpgen.prompt.response.conversation;

import java.util.List;

public class Conversation {
    private String conversationType;
    private List<ConversationMessage> messages;

    public String getConversationType() {
        return conversationType;
    }

    public void setConversationType(String conversationType) {
        this.conversationType = conversationType;
    }

    public List<ConversationMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ConversationMessage> messages) {
        this.messages = messages;
    }
}
