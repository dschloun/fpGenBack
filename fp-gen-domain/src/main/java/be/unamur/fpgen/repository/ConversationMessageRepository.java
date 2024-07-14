package be.unamur.fpgen.repository;

import be.unamur.fpgen.instant_message.ConversationMessage;

import java.util.Set;

public interface ConversationMessageRepository {
    ConversationMessage saveConversationMessage(ConversationMessage conversationInstantMessage);

    Set<ConversationMessage> saveConversationMessageList(Set<ConversationMessage> conversationInstantMessageList);

    void deleteConversationMessageById(Long conversationInstantMessageId);
}
