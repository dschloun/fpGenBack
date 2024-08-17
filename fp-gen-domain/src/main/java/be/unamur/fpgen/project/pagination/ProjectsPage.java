package be.unamur.fpgen.project.pagination;

import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.project.Project;

import java.util.List;

public class ProjectsPage {
    private final Pagination pagination;
    private final List<Project> projectList;

    private ProjectsPage(Builder builder) {
        pagination = builder.pagination;
        projectList = builder.projectList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Pagination pagination;
        private List<Project> projectList;

        private Builder() {
        }

        public Builder withPagination(Pagination val) {
            pagination = val;
            return this;
        }

        public Builder withProjectList(List<Project> val) {
            projectList = val;
            return this;
        }

        public ProjectsPage build() {
            return new ProjectsPage(this);
        }
    }
}
