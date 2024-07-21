package be.unamur.fpgen.author.pagination;

import be.unamur.fpgen.exception.pagination.IncompletePagedAuthorsQueryException;
import be.unamur.fpgen.pagination.QueryPage;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class PagedAuthorsQuery {
    private final QueryPage queryPage;
    private final AuthorQuery authorQuery;

    private PagedAuthorsQuery(Builder builder) {
        queryPage = builder.queryPage;
        authorQuery = builder.authorQuery;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public QueryPage getQueryPage() {
        return queryPage;
    }

    public AuthorQuery getAuthorQuery() {
        return authorQuery;
    }

    public static final class Builder extends ViolationHandler {
        private QueryPage queryPage;
        private AuthorQuery authorQuery;

        private Builder() {
        }

        public Builder withQueryPage(QueryPage val) {
            queryPage = val;
            return this;
        }

        public Builder withAuthorQuery(AuthorQuery val) {
            authorQuery = val;
            return this;
        }

        public PagedAuthorsQuery build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(queryPage, "queryPage"));
            violations.addAll(cannotBeNull(authorQuery, "authorQuery"));

            if (!violations.isEmpty()) {
                throw new IncompletePagedAuthorsQueryException(buildMessage("The paged author query is incomplete because", violations));
            }
            return new PagedAuthorsQuery(this);
        }
    }
}
