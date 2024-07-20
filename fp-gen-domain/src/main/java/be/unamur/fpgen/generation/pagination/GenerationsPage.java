package be.unamur.fpgen.generation.pagination;

import be.unamur.fpgen.exception.pagination.IncompleteGenerationsPageException;
import be.unamur.fpgen.generation.AbstractGeneration;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class GenerationsPage {
    private final Pagination pagination;
    private final List<AbstractGeneration> generationList;

    private GenerationsPage(Builder builder) {
        pagination = builder.pagination;
        generationList = builder.generationList;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<AbstractGeneration> getGenerationList() {
        return generationList;
    }

    public static final class Builder extends ViolationHandler {
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

        public GenerationsPage build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(pagination, "pagination"));
            violations.addAll(cannotBeNull(generationList, "generationList"));

            if (!violations.isEmpty()) {
                throw new IncompleteGenerationsPageException(buildMessage("The instant generation page is incomplete because", violations));
            }
            return new GenerationsPage(this);
        }
    }
}
