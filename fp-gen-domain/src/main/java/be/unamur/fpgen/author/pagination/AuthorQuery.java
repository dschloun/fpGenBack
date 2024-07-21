package be.unamur.fpgen.author.pagination;

public class AuthorQuery {
    private final String lastname;
    private final String firstname;
    private final String organisation;
    private final String function;
    private final String trigram;
    private final String description;
    private final String comment;
    private final String authorTrigram;

    private AuthorQuery(Builder builder) {
        lastname = builder.lastname;
        firstname = builder.firstname;
        organisation = builder.organisation;
        function = builder.function;
        trigram = builder.trigram;
        description = builder.description;
        comment = builder.comment;
        authorTrigram = builder.authorTrigram;
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getOrganisation() {
        return organisation;
    }

    public String getFunction() {
        return function;
    }

    public String getTrigram() {
        return trigram;
    }

    public String getDescription() {
        return description;
    }

    public String getComment() {
        return comment;
    }

    public String getAuthorTrigram() {
        return authorTrigram;
    }

    public static final class Builder {
        private String lastname;
        private String firstname;
        private String organisation;
        private String function;
        private String trigram;
        private String description;
        private String comment;
        private String authorTrigram;

        private Builder() {
        }

        public Builder withLastname(String val) {
            lastname = val;
            return this;
        }

        public Builder withFirstname(String val) {
            firstname = val;
            return this;
        }

        public Builder withOrganisation(String val) {
            organisation = val;
            return this;
        }

        public Builder withFunction(String val) {
            function = val;
            return this;
        }

        public Builder withTrigram(String val) {
            trigram = val;
            return this;
        }

        public Builder withDescription(String val) {
            description = val;
            return this;
        }

        public Builder withComment(String val) {
            comment = val;
            return this;
        }

        public Builder withAuthorTrigram(String val) {
            authorTrigram = val;
            return this;
        }

        public AuthorQuery build() {
            return new AuthorQuery(this);
        }
    }
}
