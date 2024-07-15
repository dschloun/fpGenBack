package be.unamur.fpgen.repository;

import be.unamur.fpgen.instant_message.ConversationMessage;

import java.util.Set;

public class JpaConversationMessageRepository implements ConversationMessageRepository{
    @Override
    public ConversationMessage saveConversationMessage(ConversationMessage conversationInstantMessage) {
        return null;
    }

    @Override
    public Set<ConversationMessage> saveConversationMessageList(Set<ConversationMessage> conversationInstantMessageList) {
        return null;
    }

    @Override
    public void deleteConversationMessageById(Long conversationInstantMessageId) {

    }
}
