package be.unamur.fpgen.project.pagination;

import be.unamur.fpgen.exception.pagination.IncompletePagedProjectsQueryException;
import be.unamur.fpgen.pagination.QueryPage;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class PagedProjectsQuery {
    private final QueryPage queryPage;
    private final ProjectQuery projectQuery;

    private PagedProjectsQuery(Builder builder) {
        queryPage = builder.queryPage;
        projectQuery = builder.projectQuery;
    }

    public QueryPage getQueryPage() {
        return queryPage;
    }

    public ProjectQuery getProjectQuery() {
        return projectQuery;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends ViolationHandler {
        private QueryPage queryPage;
        private ProjectQuery projectQuery;

        private Builder() {
        }

        public Builder withQueryPage(QueryPage val) {
            queryPage = val;
            return this;
        }

        public Builder withProjectQuery(ProjectQuery val) {
            projectQuery = val;
            return this;
        }

        public PagedProjectsQuery build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(queryPage, "queryPage"));
            violations.addAll(cannotBeNull(projectQuery, "projectQuery"));

            if (!violations.isEmpty()) {
                throw new IncompletePagedProjectsQueryException(buildMessage("The paged project query is incomplete because", violations));
            }
            return new PagedProjectsQuery(this);
        }
    }
}
