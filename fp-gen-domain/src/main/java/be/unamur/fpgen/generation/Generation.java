package be.unamur.fpgen.generation;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.instant_message.MessageTopicEnum;
import be.unamur.fpgen.instant_message.MessageTypeEnum;
import be.unamur.fpgen.utils.DateUtil;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @overview: generation is a class that represents the generation of a message, message batch, conversation or conversation batch
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

    // constructors
    private Generation(final Builder builder) {
        creationDate = builder.creationDate;
        modificationDate = builder.modificationDate;
        id = builder.id;
        generationId = builder.generationId;
        authorTrigram = builder.authorTrigram;
        details = builder.details;
        type = builder.type;
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

    // builder
    public static final class Builder {
        private OffsetDateTime creationDate;
        private OffsetDateTime modificationDate;
        private UUID id;
        private String generationId;
        private String authorTrigram;
        private String details;
        private GenerationTypeEnum type;

        public Builder() {
        }

        public Builder withCreationDate(final OffsetDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder withModificationDate(final OffsetDateTime modificationDate) {
            this.modificationDate = modificationDate;
            return this;
        }

        public Builder withId(final UUID id) {
            this.id = id;
            return this;
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

        public Generation build() {
            this.generationId = generateGenerationId();
            return new Generation(this);
        }

        // methods
        private String generateGenerationId(){
            return String.format("%s-%s-%s", this.type, this.authorTrigram, DateUtil.convertOffsetDateTimeToString(this.creationDate));
        }
    }
}
