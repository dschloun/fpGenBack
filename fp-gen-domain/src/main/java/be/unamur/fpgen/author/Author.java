package be.unamur.fpgen.author;

import be.unamur.fpgen.BaseUuidDomain;

public class Author extends BaseUuidDomain {
    private final String lastName;
    private final String firstName;
    private final String trigram;
    private final String organization;
    private final String function;
    private final String email;
    private final String phoneNumber;
    private final AuthorStatusEnum status;

    private Author(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        lastName = builder.lastName;
        firstName = builder.firstName;
        trigram = builder.trigram;
        organization = builder.organization;
        function = builder.function;
        email = builder.email;
        phoneNumber = builder.phoneNumber;
        status = builder.status;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getTrigram() {
        return trigram;
    }

    public String getOrganization() {
        return organization;
    }

    public String getFunction() {
        return function;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AuthorStatusEnum getStatus() {
        return status;
    }

    public static final class Builder extends AbstractBaseUuidDomainBuilder<Builder>{
        private String lastName;
        private String firstName;
        private String trigram;
        private String organization;
        private String function;
        private String email;
        private String phoneNumber;
        private AuthorStatusEnum status;

        public Builder() {
        }

        public Builder withLastName(String val) {
            this.lastName = val;
            return this;
        }

        public Builder withFirstName(String val) {
            this.firstName = val;
            return this;
        }

        public Builder withTrigram(String val) {
            this.trigram = val;
            return this;
        }

        public Builder withOrganization(String val) {
            this.organization = val;
            return this;
        }

        public Builder withFunction(String val) {
            this.function = val;
            return this;
        }

        public Builder withEmail(String val) {
            this.email = val;
            return this;
        }

        public Builder withPhoneNumber(String val) {
            this.phoneNumber = val;
            return this;
        }

        public Builder withStatus(AuthorStatusEnum val) {
            this.status = val;
            return this;
        }

        public Author build() {
            return new Author(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
