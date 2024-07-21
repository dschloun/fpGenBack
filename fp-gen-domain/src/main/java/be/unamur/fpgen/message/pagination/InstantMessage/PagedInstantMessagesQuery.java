package be.unamur.fpgen.message.pagination.InstantMessage;

import be.unamur.fpgen.exception.pagination.IncompletePagedInstantMessagesQueryException;
import be.unamur.fpgen.pagination.QueryPage;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class PagedInstantMessagesQuery {

    private final QueryPage queryPage;
    private final InstantMessageQuery instantMessageQuery;

    private PagedInstantMessagesQuery(Builder builder) {
        queryPage = builder.queryPage;
        instantMessageQuery = builder.instantMessageQuery;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public QueryPage getQueryPage() {
        return queryPage;
    }

    public InstantMessageQuery getInstantMessageQuery() {
        return instantMessageQuery;
    }


    public static final class Builder extends ViolationHandler {
        private QueryPage queryPage;
        private InstantMessageQuery instantMessageQuery;

        private Builder() {
        }

        public Builder withQueryPage(QueryPage val) {
            queryPage = val;
            return this;
        }

        public Builder withInstantMessageQuery(InstantMessageQuery val) {
            instantMessageQuery = val;
            return this;
        }

        public PagedInstantMessagesQuery build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(queryPage, "queryPage"));
            violations.addAll(cannotBeNull(instantMessageQuery, "instantMessageQuery"));

            if (!violations.isEmpty()) {
                throw new IncompletePagedInstantMessagesQueryException(buildMessage("The paged instant message query is incomplete because", violations));
            }
            return new PagedInstantMessagesQuery(this);
        }
    }
}
