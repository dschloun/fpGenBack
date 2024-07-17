package be.unamur.fpgen.repository;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.instant_message.ConversationMessage;

import java.util.List;

public interface ConversationMessageRepository {
    ConversationMessage saveConversationMessage(ConversationMessage conversationInstantMessage);

    List<ConversationMessage> saveConversationMessageList(Conversation conversation, List<ConversationMessage> conversationInstantMessageList);

    void deleteConversationMessageById(Long conversationInstantMessageId);
}
