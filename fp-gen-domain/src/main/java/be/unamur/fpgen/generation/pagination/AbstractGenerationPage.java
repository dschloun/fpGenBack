package be.unamur.fpgen.generation.pagination;

import be.unamur.fpgen.generation.AbstractGeneration;
import be.unamur.fpgen.pagination.Pagination;

import java.util.List;

public class AbstractGenerationPage {
    private final Pagination pagination;
    private final List<AbstractGeneration> generationList;

    private AbstractGenerationPage(Builder builder) {
        pagination = builder.pagination;
        generationList = builder.generationList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<AbstractGeneration> getGenerationList() {
        return generationList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Pagination pagination;
        private List<AbstractGeneration> generationList;

        private Builder() {
        }

        public Builder withPagination(Pagination val) {
            pagination = val;
            return this;
        }

        public Builder withGenerationList(List<AbstractGeneration> val) {
            generationList = val;
            return this;
        }

        public AbstractGenerationPage build() {
            return new AbstractGenerationPage(this);
        }
    }
}
