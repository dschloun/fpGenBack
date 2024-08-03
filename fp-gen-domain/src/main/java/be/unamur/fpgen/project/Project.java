package be.unamur.fpgen.project;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.AbstractDataset;

import java.util.HashSet;
import java.util.Set;

public class Project extends BaseUuidDomain {
    private String name;
    private String description;
    private String organisation;
    private String businessId;
    private Author author;
    private Set<AbstractDataset> datasetList = new HashSet<>();

    private Project(Builder builder) {
        name = builder.name;
        description = builder.description;
        organisation = builder.organisation;
        businessId = builder.businessId;
        author = builder.author;
        datasetList = builder.datasetList;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOrganisation() {
        return organisation;
    }

    public String getBusinessId() {
        return businessId;
    }

    public Author getAuthor() {
        return author;
    }

    public Set<AbstractDataset> getDatasetList() {
        return datasetList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{
        private String name;
        private String description;
        private String organisation;
        private String businessId;
        private Author author;
        private Set<AbstractDataset> datasetList;

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

        public Builder withOrganisation(String val) {
            organisation = val;
            return this;
        }

        public Builder withBusinessId(String val) {
            businessId = val;
            return this;
        }

        public Builder withAuthor(Author val) {
            author = val;
            return this;
        }

        public Builder withDatasetList(Set<AbstractDataset> val) {
            datasetList = val;
            return this;
        }

        public Project build() {
            return new Project(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
