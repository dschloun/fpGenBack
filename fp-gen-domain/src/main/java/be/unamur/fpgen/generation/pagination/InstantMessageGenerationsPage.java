package be.unamur.fpgen.generation.pagination;

import be.unamur.fpgen.exception.pagination.IncompleteGenerationsPageException;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class InstantMessageGenerationsPage {
    private final Pagination pagination;
    private final List<InstantMessageGeneration> generationList;

    private InstantMessageGenerationsPage(Builder builder) {
        pagination = builder.pagination;
        generationList = builder.generationList;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<InstantMessageGeneration> getGenerationList() {
        return generationList;
    }

    public static final class Builder extends ViolationHandler {
        private Pagination pagination;
        private List<InstantMessageGeneration> generationList;

        private Builder() {
        }

        public Builder withPagination(Pagination val) {
            pagination = val;
            return this;
        }

        public Builder withGenerationList(List<InstantMessageGeneration> val) {
            generationList = val;
            return this;
        }

        public InstantMessageGenerationsPage build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(pagination, "pagination"));
            violations.addAll(cannotBeNull(generationList, "generationList"));

            if (!violations.isEmpty()) {
                throw new IncompleteGenerationsPageException(buildMessage("The instant generation page is incomplete because", violations));
            }
            return new InstantMessageGenerationsPage(this);
        }
    }
}
