package be.unamur.fpgen.conversation.pagination;

import be.unamur.fpgen.conversation.Conversation;
import be.unamur.fpgen.exception.pagination.IncompleteConversationsPageException;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class ConversationsPage {
    private final Pagination pagination;
    private final List<Conversation> conversationList;

    private ConversationsPage(Builder builder) {
        pagination = builder.pagination;
        conversationList = builder.conversationList;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<Conversation> getConversationList() {
        return conversationList;
    }

    public static final class Builder extends ViolationHandler {
        private Pagination pagination;
        private List<Conversation> conversationList;

        private Builder() {
        }

        public Builder withPagination(Pagination val) {
            pagination = val;
            return this;
        }

        public Builder withConversationList(List<Conversation> val) {
            conversationList = val;
            return this;
        }

        public ConversationsPage build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(pagination, "pagination"));
            violations.addAll(cannotBeNull(conversationList, "conversationList"));

            if (!violations.isEmpty()) {
                throw new IncompleteConversationsPageException(buildMessage("The instant messages page is incomplete because", violations));
            }
            return new ConversationsPage(this);
        }
    }
}
