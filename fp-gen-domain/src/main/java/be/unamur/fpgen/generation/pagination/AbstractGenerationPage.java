package be.unamur.fpgen.generation.pagination;

import be.unamur.fpgen.pagination.Pagination;

public abstract class AbstractGenerationPage {
    private final Pagination pagination;

    protected AbstractGenerationPage(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {
        return pagination;
    }

    protected abstract static class AbstractGenerationPageBuilder<T> {
        private Pagination pagination;

        public Pagination getPagination() {
            return pagination;
        }

        public T withPagination(Pagination val) {
            pagination = val;
            return self();
        }

        protected abstract T self();
    }
}
