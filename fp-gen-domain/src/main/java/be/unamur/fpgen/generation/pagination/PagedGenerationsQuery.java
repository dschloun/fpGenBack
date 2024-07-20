package be.unamur.fpgen.generation.pagination;

import be.unamur.fpgen.exception.pagination.IncompletePagedGenerationsQueryException;
import be.unamur.fpgen.pagination.QueryPage;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class PagedGenerationsQuery {
    private final QueryPage queryPage;
    private final GenerationQuery generationQuery;

    private PagedGenerationsQuery(Builder builder) {
        queryPage = builder.queryPage;
        generationQuery = builder.generationQuery;
    }

    public QueryPage getQueryPage() {
        return queryPage;
    }

    public GenerationQuery getGenerationQuery() {
        return generationQuery;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends ViolationHandler {
        private QueryPage queryPage;
        private GenerationQuery generationQuery;

        private Builder() {
        }

        public Builder withQueryPage(QueryPage val) {
            queryPage = val;
            return this;
        }

        public Builder withGenerationQuery(GenerationQuery val) {
            generationQuery = val;
            return this;
        }

        public PagedGenerationsQuery build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(queryPage, "queryPage"));
            violations.addAll(cannotBeNull(generationQuery, "generationQuery"));

            if (!violations.isEmpty()) {
                throw new IncompletePagedGenerationsQueryException(buildMessage("The paged generation query is incomplete because", violations));
            }
            return new PagedGenerationsQuery(this);
        }
    }
}
