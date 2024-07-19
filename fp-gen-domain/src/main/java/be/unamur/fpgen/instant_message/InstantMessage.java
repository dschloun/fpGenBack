package be.unamur.fpgen.instant_message;

import java.util.UUID;

public class InstantMessage extends AbstractInstantMessage {
    private final UUID generationTechniqueId;
    private final String generationBusinessId;

    public UUID getGenerationTechniqueId() {
        return generationTechniqueId;
    }

    public String getGenerationBusinessId() {
        return generationBusinessId;
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
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    // builder
    public static final class Builder extends AbstractInstantMessageBuilder<Builder> {
        private UUID generationTechniqueId;
        private String generationBusinessId;

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

        public InstantMessage build() {
            return new InstantMessage(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}