package be.unamur.fpgen.project.pagination;

import be.unamur.fpgen.project.ProjectTypeEnum;

import java.time.OffsetDateTime;

public class ProjectQuery {
    private final ProjectTypeEnum type;
    private final String name;
    private final String description;
    private final String organization;
    private final String authorTrigram;
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;

    private ProjectQuery(Builder builder) {
        type = builder.type;
        name = builder.name;
        description = builder.description;
        organization = builder.organization;
        authorTrigram = builder.authorTrigram;
        startDate = builder.startDate;
        endDate = builder.endDate;
    }

    public ProjectTypeEnum getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOrganization() {
        return organization;
    }

    public String getAuthorTrigram() {
        return authorTrigram;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private ProjectTypeEnum type;
        private String name;
        private String description;
        private String organization;
        private String authorTrigram;
        private OffsetDateTime startDate;
        private OffsetDateTime endDate;

        private Builder() {
        }

        public Builder withType(ProjectTypeEnum val) {
            type = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withDescription(String val) {
            description = val;
            return this;
        }

        public Builder withOrganization(String val) {
            organization = val;
            return this;
        }

        public Builder withAuthorTrigram(String val) {
            authorTrigram = val;
            return this;
        }

        public Builder withStartDate(OffsetDateTime val) {
            startDate = val;
            return this;
        }

        public Builder withEndDate(OffsetDateTime val) {
            endDate = val;
            return this;
        }

        public ProjectQuery build() {
            return new ProjectQuery(this);
        }
    }
}