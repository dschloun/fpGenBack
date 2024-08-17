package be.unamur.fpgen.project.pagination;

import java.time.OffsetDateTime;

public class ProjectQuery {
    private final String name;
    private final String description;
    private final String organization;
    private final String authorTrigram;
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;

    private ProjectQuery(Builder builder) {
        name = builder.name;
        description = builder.description;
        organization = builder.organization;
        authorTrigram = builder.authorTrigram;
        startDate = builder.startDate;
        endDate = builder.endDate;
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
        private String name;
        private String description;
        private String organization;
        private String authorTrigram;
        private OffsetDateTime startDate;
        private OffsetDateTime endDate;

        private Builder() {
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

//properties:
//name:
//type: string
//description:
//type: string
//organization:
//type: string
//authorTrigram:
//type: string
//startDate:
//$ref: 'common.yaml#/components/schemas/Date'
//endDate:
//$ref: 'common.yaml#/components/schemas/Date'