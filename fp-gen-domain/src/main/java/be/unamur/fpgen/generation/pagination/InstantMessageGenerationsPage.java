package be.unamur.fpgen.generation.pagination;

import be.unamur.fpgen.generation.InstantMessageGeneration;

import java.util.List;

public class InstantMessageGenerationsPage extends AbstractGenerationPage{

    private final List<InstantMessageGeneration> generationList;

    private InstantMessageGenerationsPage(Builder builder) {
        super(builder.getPagination());
        generationList = builder.generationList;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public List<InstantMessageGeneration> getGenerationList() {
        return generationList;
    }

    public static final class Builder extends AbstractGenerationPageBuilder<Builder>  {

        private List<InstantMessageGeneration> generationList;

        private Builder() {
        }

        public Builder withGenerationList(List<InstantMessageGeneration> val) {
            generationList = val;
            return this;
        }

        public InstantMessageGenerationsPage build() {
            return new InstantMessageGenerationsPage(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
