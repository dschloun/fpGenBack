package be.unamur.fpgen.conversation.pagination;

import be.unamur.fpgen.exception.pagination.IncompletePagedConversationsQueryException;
import be.unamur.fpgen.pagination.QueryPage;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class PagedConversationsQuery {
    private final QueryPage queryPage;
    private final ConversationQuery conversationQuery;

    public QueryPage getQueryPage() {
        return queryPage;
    }

    public ConversationQuery getConversationQuery() {
        return conversationQuery;
    }

    private PagedConversationsQuery(Builder builder) {
        queryPage = builder.queryPage;
        conversationQuery = builder.conversationQuery;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends ViolationHandler {
        private QueryPage queryPage;
        private ConversationQuery conversationQuery;

        private Builder() {
        }

        public Builder withQueryPage(QueryPage val) {
            queryPage = val;
            return this;
        }

        public Builder withConversationQuery(ConversationQuery val) {
            conversationQuery = val;
            return this;
        }

        public PagedConversationsQuery build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(queryPage, "queryPage"));
            violations.addAll(cannotBeNull(conversationQuery, "conversationQuery"));

            if (!violations.isEmpty()) {
                throw new IncompletePagedConversationsQueryException(buildMessage("The paged conversation query is incomplete because", violations));
            }
            return new PagedConversationsQuery(this);
        }
    }
}
