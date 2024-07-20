package be.unamur.fpgen.pagination;


import be.unamur.fpgen.exception.IncompleteQueryPageException;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class QueryPage {
    //members
    private Integer page;
    private Integer size;

    //constructor
    private QueryPage(Builder builder) {
        page = builder.page;
        size = builder.size;
    }

    //getters
    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    //builder
    public static final class Builder extends ViolationHandler {
        private Integer page;
        private Integer size;

        public Builder() {
        }

        public Builder page(Integer page) {
            this.page = page;
            return this;
        }

        public Builder size(Integer size) {
            this.size = size;
            return this;
        }

        public QueryPage build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(page, "page"));
            violations.addAll(cannotBeNull(size, "size"));

            if (!violations.isEmpty()) {
                throw new IncompleteQueryPageException(buildMessage("The query page is incomplete because", violations));
            }
            return new QueryPage(this);
        }
    }
}
