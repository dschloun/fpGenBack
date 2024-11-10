package be.unamur.fpgen.prompt.response.conversation;

import java.util.List;

public class ConversationResponse {
    private List<Conversation> generations;

    public List<Conversation> getGenerations() {
        return generations;
    }

    public void setGenerations(List<Conversation> generations) {
        this.generations = generations;
    }
}
