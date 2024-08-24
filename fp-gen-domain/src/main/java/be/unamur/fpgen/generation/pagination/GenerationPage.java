package be.unamur.fpgen.generation.pagination;

import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.pagination.Pagination;

import java.util.List;

public class GenerationPage {
    private final Pagination pagination;
    private final List<Generation> generationList;

    private GenerationPage(Builder builder) {
        pagination = builder.pagination;
        generationList = builder.generationList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<Generation> getGenerationList() {
        return generationList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Pagination pagination;
        private List<Generation> generationList;

        private Builder() {
        }

        public Builder withPagination(Pagination val) {
            pagination = val;
            return this;
        }

        public Builder withGenerationList(List<Generation> val) {
            generationList = val;
            return this;
        }

        public GenerationPage build() {
            return new GenerationPage(this);
        }
    }
}
