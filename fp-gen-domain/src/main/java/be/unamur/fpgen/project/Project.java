package be.unamur.fpgen.project;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.HasAuthor;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.*;
import be.unamur.fpgen.utils.DateUtil;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Project is composed of 3 datasets (TRAINING, TEST, VALIDATION) and is created by an Author.
 * it has an organisation, a businessId, a name and a description.
 */
public class Project extends BaseUuidDomain implements HasAuthor {
    private final ProjectTypeEnum type;
    private final String name;
    private final String description;
    private final String organisation;
    private final String businessId;
    private final Author author;
    private Set<Dataset> datasetList = new HashSet<>();

    private Project(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        type = builder.type;
        name = builder.name;
        description = builder.description;
        organisation = builder.organisation;
        businessId = builder.businessId;
        author = builder.author;
        datasetList.addAll(builder.datasetList);
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

    public String getOrganisation() {
        return organisation;
    }

    public String getBusinessId() {
        return businessId;
    }

    public Author getAuthor() {
        return author;
    }

    public Set<Dataset> getDatasetList() {
        return datasetList;
    }

    public void generateInitialDatasets(final DatasetTypeEnum datasetType){
        final Set<Dataset> datasets = new HashSet<>();
        datasets.add(this.generateDataset(this.getAuthor(), datasetType, DatasetFunctionEnum.TRAINING));
        datasets.add(this.generateDataset(this.getAuthor(), datasetType, DatasetFunctionEnum.TEST));
        datasets.add(this.generateDataset(this.getAuthor(), datasetType, DatasetFunctionEnum.VALIDATION));
        this.datasetList.addAll(datasets);
    }

    private Dataset generateDataset(Author author, DatasetTypeEnum datasetType, DatasetFunctionEnum datasetFunctionEnum){
            return Dataset.newBuilder()
                    .withType(datasetType)
                    .withAuthor(author)
                    .withDatasetFunction(datasetFunctionEnum)
                    .withName(generateDatasetName(datasetFunctionEnum))
                    .withVersion(0)
                    .withLastVersion(true)
                    .build();
    }

    private String generateDatasetName(DatasetFunctionEnum datasetFunctionEnum){
        return String.format("%s-%s", this.getName(), datasetFunctionEnum.name());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{
        private ProjectTypeEnum type;
        private String name;
        private String description;
        private String organisation;
        private String businessId;
        private Author author;
        private Set<Dataset> datasetList= new HashSet<>();

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

        public Builder withDatasetList(Set<Dataset> val) {
            datasetList = val;
            return this;
        }

        public Project build() {
            businessId = generateBusinessId();
            return new Project(this);
        }

        @Override
        protected Builder self() {
            return this;
        }

        public String generateBusinessId() {
            return String.format("%s-%s-%s", this.author.getTrigram(), this.name, DateUtil.convertOffsetDateTimeToString(OffsetDateTime.now()));
        }
    }
}
