package be.unamur.fpgen.dataset.pagination;

import be.unamur.fpgen.dataset.DatasetTypeEnum;

import java.time.OffsetDateTime;

public class DatasetQuery {

    private final Integer version;
    private final String name;
    private final String description;
    private final String comment;
    private final String authorTrigram;
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;
    private final DatasetTypeEnum type;

    private DatasetQuery(Builder builder) {
        version = builder.version;
        name = builder.name;
        description = builder.description;
        comment = builder.comment;
        authorTrigram = builder.authorTrigram;
        startDate = builder.startDate;
        endDate = builder.endDate;
        type = builder.type;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public Integer getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getComment() {
        return comment;
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

    public DatasetTypeEnum getType() {
        return type;
    }

    public static final class Builder {
        private Integer version;
        private String name;
        private String description;
        private String comment;
        private String authorTrigram;
        private OffsetDateTime startDate;
        private OffsetDateTime endDate;
        private DatasetTypeEnum type;

        private Builder() {
        }

        public Builder withVersion(Integer val) {
            version = val;
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

        public Builder withComment(String val) {
            comment = val;
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

        public Builder withType(DatasetTypeEnum val) {
            type = val;
            return this;
        }

        public DatasetQuery build() {
            return new DatasetQuery(this);
        }
    }
}
