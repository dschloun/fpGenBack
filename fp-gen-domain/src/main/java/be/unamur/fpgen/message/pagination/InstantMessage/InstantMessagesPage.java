package be.unamur.fpgen.message.pagination.InstantMessage;

import be.unamur.fpgen.exception.pagination.IncompleteInstantMessagesPageException;
import be.unamur.fpgen.message.InstantMessage;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class InstantMessagesPage {

    private final Pagination pagination;
    private final List<InstantMessage> instantMessageList;

    private InstantMessagesPage(Builder builder) {
        pagination = builder.pagination;
        instantMessageList = builder.instantMessageList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<InstantMessage> getInstantMessageList() {
        return instantMessageList;
    }


    public static final class Builder extends ViolationHandler {
        private Pagination pagination;
        private List<InstantMessage> instantMessageList;

        private Builder() {
        }

        public Builder withPagination(Pagination val) {
            pagination = val;
            return this;
        }

        public Builder withInstantMessageList(List<InstantMessage> val) {
            instantMessageList = val;
            return this;
        }

        public InstantMessagesPage build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(pagination, "pagination"));
            violations.addAll(cannotBeNull(instantMessageList, "instantMessageList"));

            if (!violations.isEmpty()) {
                throw new IncompleteInstantMessagesPageException(buildMessage("The instant messages page is incomplete because", violations));
            }
            return new InstantMessagesPage(this);
        }
    }
}
