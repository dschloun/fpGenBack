package be.unamur.fpgen.generation.pagination;

import be.unamur.fpgen.exception.pagination.IncompleteGenerationsPageException;
import be.unamur.fpgen.generation.AbstractGeneration;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class GenerationsPage {
    private final Pagination pagination;
    private final List<ConversationGeneration> conversationGenerations;
    private final List<InstantMessageGeneration> instantMessageGenerations;

    private GenerationsPage(Builder builder) {
        pagination = builder.pagination;
        conversationGenerations = builder.conversationGenerations;
        instantMessageGenerations = builder.instantMessageGenerations;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<InstantMessageGeneration> getInstantMessageGenerations() {
        return instantMessageGenerations;
    }

    public List<ConversationGeneration> getConversationGenerations() {
        return conversationGenerations;
    }

    public static final class Builder extends ViolationHandler {
        private Pagination pagination;
        private List<ConversationGeneration> conversationGenerations;
        private List<InstantMessageGeneration> instantMessageGenerations;

        private Builder() {
        }

        public Builder withPagination(Pagination val) {
            pagination = val;
            return this;
        }

        public Builder withConversationGenerationList(List<ConversationGeneration> val) {
            conversationGenerations = val;
            return this;
        }
        public Builder withInstantMessageGenerationList(List<InstantMessageGeneration> val) {
            instantMessageGenerations = val;
            return this;
        }

        public GenerationsPage build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(pagination, "pagination"));

            if (!violations.isEmpty()) {
                throw new IncompleteGenerationsPageException(buildMessage("The instant generation page is incomplete because", violations));
            }
            return new GenerationsPage(this);
        }
    }
}
