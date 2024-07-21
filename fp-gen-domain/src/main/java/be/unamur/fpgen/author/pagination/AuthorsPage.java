package be.unamur.fpgen.author.pagination;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.exception.pagination.IncompleteAuthorsPageException;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.utils.ViolationHandler;

import java.util.ArrayList;
import java.util.List;

public class AuthorsPage {
    private final Pagination pagination;
    private final List<Author> authorList;

    private AuthorsPage(Builder builder) {
        pagination = builder.pagination;
        authorList = builder.authorList;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public static final class Builder extends ViolationHandler {
        private Pagination pagination;
        private List<Author> authorList;

        private Builder() {
        }



        public Builder withPagination(Pagination val) {
            pagination = val;
            return this;
        }

        public Builder withAuthorList(List<Author> val) {
            authorList = val;
            return this;
        }

        public AuthorsPage build() {
            // Validation
            List<String> violations = new ArrayList<>();
            violations.addAll(cannotBeNull(pagination, "pagination"));
            violations.addAll(cannotBeNull(authorList, "authorList"));

            if (!violations.isEmpty()) {
                throw new IncompleteAuthorsPageException(buildMessage("The authors page is incomplete because", violations));
            }
            return new AuthorsPage(this);
        }
    }
}
