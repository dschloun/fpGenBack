package be.unamur.fpgen.generation.pagination;

import be.unamur.fpgen.exception.pagination.IncompleteGenerationsPageException;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class ConversationGenerationsPage {
    private final Pagination pagination;
    private final List<ConversationGeneration> generationList;

    private ConversationGenerationsPage(Builder builder) {
        pagination = builder.pagination;
        generationList = builder.generationList;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<ConversationGeneration> getGenerationList() {
        return generationList;
    }

    public static final class Builder extends ViolationHandler {
        private Pagination pagination;
        private List<ConversationGeneration> generationList;

        private Builder() {
        }

        public Builder withPagination(Pagination val) {
            pagination = val;
            return this;
        }

        public Builder withGenerationList(List<ConversationGeneration> val) {
            generationList = val;
            return this;
        }

        public ConversationGenerationsPage build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(pagination, "pagination"));
            violations.addAll(cannotBeNull(generationList, "generationList"));

            if (!violations.isEmpty()) {
                throw new IncompleteGenerationsPageException(buildMessage("The instant generation page is incomplete because", violations));
            }
            return new ConversationGenerationsPage(this);
        }
    }
}
