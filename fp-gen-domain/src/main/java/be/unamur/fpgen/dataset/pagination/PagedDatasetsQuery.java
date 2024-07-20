package be.unamur.fpgen.dataset.pagination;

import be.unamur.fpgen.exception.pagination.IncompletePagedConversationsQueryException;
import be.unamur.fpgen.pagination.QueryPage;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class PagedDatasetsQuery {
    private final QueryPage queryPage;
    private final DatasetQuery datasetQuery;

    public QueryPage getQueryPage() {
        return queryPage;
    }

    public DatasetQuery getDatasetQuery() {
        return datasetQuery;
    }

    private PagedDatasetsQuery(Builder builder) {
        queryPage = builder.queryPage;
        datasetQuery = builder.datasetQuery;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends ViolationHandler {
        private QueryPage queryPage;
        private DatasetQuery datasetQuery;

        private Builder() {
        }

        public Builder withQueryPage(QueryPage val) {
            queryPage = val;
            return this;
        }

        public Builder withDatasetQuery(DatasetQuery val) {
            datasetQuery = val;
            return this;
        }

        public PagedDatasetsQuery build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(queryPage, "queryPage"));
            violations.addAll(cannotBeNull(datasetQuery, "DatasetQuery"));

            if (!violations.isEmpty()) {
                throw new IncompletePagedConversationsQueryException(buildMessage("The paged dataset query is incomplete because", violations));
            }
            return new PagedDatasetsQuery(this);
        }
    }
}
