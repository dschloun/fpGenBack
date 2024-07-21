package be.unamur.fpgen.message.pagination.conversation_message;

import be.unamur.fpgen.exception.pagination.IncompletePagedConversationMessagesQueryException;
import be.unamur.fpgen.pagination.QueryPage;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class PagedConversationMessagesQuery {
    private final QueryPage queryPage;
    private final ConversationMessageQuery conversationMessageQuery;

    private PagedConversationMessagesQuery(Builder builder) {
        queryPage = builder.queryPage;
        conversationMessageQuery = builder.conversationMessageQuery;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public QueryPage getQueryPage() {
        return queryPage;
    }

    public ConversationMessageQuery getConversationMessageQuery() {
        return conversationMessageQuery;
    }

    public static final class Builder extends ViolationHandler {
        private QueryPage queryPage;
        private ConversationMessageQuery conversationMessageQuery;

        private Builder() {
        }

        public Builder withQueryPage(QueryPage val) {
            queryPage = val;
            return this;
        }

        public Builder withConversationMessageQuery(ConversationMessageQuery val) {
            conversationMessageQuery = val;
            return this;
        }

        public PagedConversationMessagesQuery build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(queryPage, "queryPage"));
            violations.addAll(cannotBeNull(conversationMessageQuery, "conversationMessageQuery"));

            if (!violations.isEmpty()) {
                throw new IncompletePagedConversationMessagesQueryException(buildMessage("The paged conversation message query is incomplete because", violations));
            }
            return new PagedConversationMessagesQuery(this);
        }
    }
}
