package be.unamur.fpgen.generation.ongoing_generation;

import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.generation.GenerationTypeEnum;

import java.util.HashSet;
import java.util.Set;

public class OngoingGeneration extends BaseUuidDomain {

    private final GenerationTypeEnum type;
    private final Set<OngoingItem> ongoingGenerationItemList = new HashSet<>();

    private OngoingGeneration(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        type = builder.type;
        ongoingGenerationItemList.addAll(builder.ongoingGenerationItemList);
    }

    public GenerationTypeEnum getType() {
        return type;
    }

    public Set<OngoingItem> getOngoingGenerationItemList() {
        return ongoingGenerationItemList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{

        private GenerationTypeEnum type;
        private Set<OngoingItem> ongoingGenerationItemList = new HashSet<>();

        private Builder() {
        }


        public Builder withType(GenerationTypeEnum val) {
            type = val;
            return this;
        }

        public Builder withOngoingGenerationItemList(Set<OngoingItem> val) {
            ongoingGenerationItemList = val;
            return this;
        }

        public OngoingGeneration build() {
            return new OngoingGeneration(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
