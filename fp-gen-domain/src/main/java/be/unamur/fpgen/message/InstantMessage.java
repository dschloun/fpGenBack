package be.unamur.fpgen.message;

import java.util.UUID;

/**
 * Represents an instant message
 * The hash is used to ensure an unicity of message
 */
public class InstantMessage extends AbstractInstantMessage {
    private final UUID generationTechniqueId;
    private final String generationBusinessId;
    private final String hash;

    public UUID getGenerationTechniqueId() {
        return generationTechniqueId;
    }

    public String getGenerationBusinessId() {
        return generationBusinessId;
    }

    public String getHash() {
        return hash;
    }

    // constructors
    public InstantMessage(final Builder builder) {
        super(builder.getId(),
                builder.getCreationDate(),
                builder.getModificationDate(),
                builder.getTopic(),
                builder.getType(),
                builder.getContent(),
                builder.getGeneration(),
                builder.isBatch());
        generationTechniqueId = builder.generationTechniqueId;
        generationBusinessId = builder.generationBusinessId;
        hash = builder.hash;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    // builder
    public static final class Builder extends AbstractInstantMessageBuilder<Builder> {
        private UUID generationTechniqueId;
        private String generationBusinessId;
        private String hash;

        Builder() {
        }

        public Builder withGenerationTechniqueId(UUID generationTechniqueId) {
            this.generationTechniqueId = generationTechniqueId;
            return this;
        }

        public Builder withGenerationBusinessId(String generationBusinessId) {
            this.generationBusinessId = generationBusinessId;
            return this;
        }

        public Builder withHash(String val){
            hash = val;
            return this;
        }

        public InstantMessage build() {
            return new InstantMessage(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}