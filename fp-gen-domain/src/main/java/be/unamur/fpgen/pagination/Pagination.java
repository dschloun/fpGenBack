package be.unamur.fpgen.pagination;

import be.unamur.fpgen.exception.pagination.general.IncompletePaginationException;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
    //members
    private Integer page;
    private Integer size;
    private Integer totalSize;

    //constructor
    private Pagination(Builder builder) {
        page = builder.page;
        size = builder.size;
        totalSize = builder.totalSize;
    }

    //getters
    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    //builder
    public static final class Builder extends ViolationHandler {
        private Integer page;
        private Integer size;
        private Integer totalSize;

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

        public Builder totalSize(Integer totalSize) {
            this.totalSize = totalSize;
            return this;
        }

        public Pagination build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(page, "page"));
            violations.addAll(cannotBeNull(size, "size"));
            violations.addAll(cannotBeNull(totalSize, "totalSize"));

            if (!violations.isEmpty()) {
                throw new IncompletePaginationException(buildMessage("The pagination is incomplete because", violations));
            }
            return new Pagination(this);
        }
    }
}
