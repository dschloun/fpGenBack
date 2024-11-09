package be.unamur.fpgen.prompt.response.conversation;

import java.util.List;

public class Conversation {
    private String conversationType;
    private List<ConversationMessage> contents;

    public String getConversationType() {
        return conversationType;
    }

    public void setConversationType(String conversationType) {
        this.conversationType = conversationType;
    }

    public List<ConversationMessage> getContents() {
        return contents;
    }

    public void setContents(List<ConversationMessage> contents) {
        this.contents = contents;
    }
}
