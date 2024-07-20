package be.unamur.fpgen.dataset.pagination;


import be.unamur.fpgen.dataset.ConversationDataset;
import be.unamur.fpgen.dataset.InstantMessageDataset;
import be.unamur.fpgen.exception.pagination.IncompleteDatasetsPageException;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class DatasetsPage {
    private final Pagination pagination;
    private final List<ConversationDataset> conversationDatasetList;
    private final List<InstantMessageDataset> instantMessageDatasetList;

    private DatasetsPage(Builder builder) {
        pagination = builder.pagination;
        conversationDatasetList = builder.conversationList;
        instantMessageDatasetList = builder.instantMessageDatasetList;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<ConversationDataset> getConversationDatasetList() {
        return conversationDatasetList;
    }

    public List<InstantMessageDataset> getInstantMessageDatasetList() {
        return instantMessageDatasetList;
    }

    public static final class Builder extends ViolationHandler {
        private Pagination pagination;
        private List<ConversationDataset> conversationList;
        private List<InstantMessageDataset> instantMessageDatasetList;

        private Builder() {
        }

        public Builder withPagination(Pagination val) {
            pagination = val;
            return this;
        }

        public Builder withConversationDatasetList(List<ConversationDataset> val) {
            conversationList = val;
            return this;
        }

        public Builder withInstantMessageDatasetList(List<InstantMessageDataset> val) {
            instantMessageDatasetList = val;
            return this;
        }

        public DatasetsPage build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(pagination, "pagination"));

            if (!violations.isEmpty()) {
                throw new IncompleteDatasetsPageException(buildMessage("The dataset page is incomplete because", violations));
            }
            return new DatasetsPage(this);
        }
    }
}
