package be.unamur.fpgen.generation;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.utils.DateUtil;

import java.time.OffsetDateTime;

/**
 * @overview: generation is a class that represents the generation of a message, message batch, conversation or conversation batch
 * Generation is immutable.
 * @specfield generationId: String // technical identifier of the generation
 * @specfield generationDate: OffsetDateTime // date of the generation
 * @specfield authorTrigram: String // author trigram of the generation
 * format 'trigram' ex: JDO (John Doe)
 * @specfield details: String // details of the generation
 */
public class Generation extends BaseUuidDomain {
    // members
    private final String generationId;
    private final String authorTrigram;
    private final String details;
    private final GenerationTypeEnum type;
    private final boolean batch;

    // constructors
    private Generation(final Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        generationId = builder.generationId;
        authorTrigram = builder.authorTrigram;
        details = builder.details;
        type = builder.type;
        batch = builder.batch;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    // getters
    public String getGenerationId() {
        return generationId;
    }

    public OffsetDateTime getGenerationDate() {
        return creationDate;
    }

    public String getAuthorTrigram() {
        return authorTrigram;
    }

    public String getDetails() {
        return details;
    }

    public GenerationTypeEnum getType() {
        return type;
    }

    public boolean isBatch() {
        return batch;
    }

    // methods
    public boolean isInstantMessageGeneration() {
        return GenerationTypeEnum.IM.equals(type);
    }

    public boolean isConversationGeneration() {
        return GenerationTypeEnum.C.equals(type);
    }

    // builder
    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder> {
        private String generationId;
        private String authorTrigram;
        private String details;
        private GenerationTypeEnum type;
        private boolean batch;

        public Builder() {
        }

        public Builder withAuthorTrigram(final String authorTrigram) {
            this.authorTrigram = authorTrigram;
            return this;
        }

        public Builder withDetails(final String details) {
            this.details = details;
            return this;
        }

        public Builder withType(final GenerationTypeEnum type) {
            this.type = type;
            return this;
        }

        public Builder withBatch(final boolean batch) {
            this.batch = batch;
            return this;
        }

        public Generation build() {
            this.generationId = generateGenerationId();
            return new Generation(this);
        }

        // methods
        private String generateGenerationId(){
            return String.format("%s-%s-%s", this.type, this.authorTrigram, DateUtil.convertOffsetDateTimeToString(OffsetDateTime.now()));
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
