package be.unamur.fpgen.message.pagination.conversation_message;

import be.unamur.fpgen.exception.pagination.IncompleteConversationMessagesPageException;
import be.unamur.fpgen.message.ConversationMessage;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class ConversationMessagesPage {
    private final Pagination pagination;
    private final List<ConversationMessage> conversationMessageList;

    private ConversationMessagesPage(Builder builder) {
        pagination = builder.pagination;
        conversationMessageList = builder.conversationMessageList;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public List<ConversationMessage> getConversationMessageList() {
        return conversationMessageList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public static final class Builder extends ViolationHandler {
        private Pagination pagination;
        private List<ConversationMessage> conversationMessageList;

        private Builder() {
        }

        public Builder withPagination(Pagination val) {
            pagination = val;
            return this;
        }

        public Builder withConversationMessageList(List<ConversationMessage> val) {
            conversationMessageList = val;
            return this;
        }

        public ConversationMessagesPage build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(pagination, "pagination"));
            violations.addAll(cannotBeNull(conversationMessageList, "conversationMessageList"));

            if (!violations.isEmpty()) {
                throw new IncompleteConversationMessagesPageException(buildMessage("The conversation messages page is incomplete because", violations));
            }
            return new ConversationMessagesPage(this);
        }
    }
}
