package be.unamur.fpgen.generation;

import be.unamur.fpgen.message.InstantMessage;
import be.unamur.fpgen.utils.DateUtil;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

public class InstantMessageGeneration extends AbstractGeneration {
    private final Set<InstantMessage> instantMessageList = new HashSet<>();

    private InstantMessageGeneration(Builder builder) {
        super(builder.getId(),
                builder.getCreationDate(),
                builder.getModificationDate(),
                builder.getAuthor(),
                builder.getDetails(),
                builder.getQuantity(),
                builder.getType(),
                builder.getTopic(),
                builder.getSystemPrompt(),
                builder.getUserPrompt());
        instantMessageList.addAll(builder.instantMessageList);
        generationId = builder.getGenerationId();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Set<InstantMessage> getInstantMessageList() {
        return instantMessageList;
    }

    public static final class Builder extends AbstractGenerationBuilder<Builder>{
        private Set<InstantMessage> instantMessageList = new HashSet<>();

        private Builder() {
        }

        public Builder withInstantMessageList(Set<InstantMessage> val) {
            instantMessageList = val;
            return this;
        }

        public InstantMessageGeneration build() {
            generationId = generateGenerationId();
            return new InstantMessageGeneration(this);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public String generateGenerationId() {
            return String.format("%s-%s-%s-%s", "IM", this.returnBatchOrSingle(), this.getAuthor().getTrigram(), DateUtil.convertOffsetDateTimeMillisToString(OffsetDateTime.now()));
        }
    }
}
