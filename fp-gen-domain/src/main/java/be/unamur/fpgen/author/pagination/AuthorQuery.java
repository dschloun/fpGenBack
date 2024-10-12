package be.unamur.fpgen.author.pagination;

import be.unamur.fpgen.author.AuthorStatusEnum;

public class AuthorQuery {
    private final String lastname;
    private final String firstname;
    private final String organization;
    private final String function;
    private final String trigram;
    private final String email;
    private final AuthorStatusEnum status;

    private AuthorQuery(Builder builder) {
        lastname = builder.lastname;
        firstname = builder.firstname;
        organization = builder.organization;
        function = builder.function;
        trigram = builder.trigram;
        email = builder.email;
        status = builder.status;
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

    public String getOrganization() {
        return organization;
    }

    public String getFunction() {
        return function;
    }

    public String getTrigram() {
        return trigram;
    }

    public String getEmail() {
        return email;
    }

    public AuthorStatusEnum getStatus() {
        return status;
    }

    public static final class Builder {
        private String lastname;
        private String firstname;
        private String organization;
        private String function;
        private String trigram;
        private String email;
        private AuthorStatusEnum status;

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

        public Builder withOrganization(String val) {
            organization = val;
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

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withStatus(AuthorStatusEnum val) {
            status = val;
            return this;
        }

        public AuthorQuery build() {
            return new AuthorQuery(this);
        }
    }
}
