package be.unamur.fpgen.author;

import be.unamur.fpgen.BaseUuidDomain;

/**
 * Represents an author of a message, conversation, project, dataset or prompt
 * It's a user of the application
 */
public class Author extends BaseUuidDomain {
    private final String lastName;
    private final String firstName;
    private final String trigram;
    private final String organization;
    private final String function;
    private final String email;
    private final String phoneNumber;
    private AuthorStatusEnum status;
    private boolean acceptTermsOfUse;
    private final String motivation;
    private boolean accountCreated;

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
        acceptTermsOfUse = builder.acceptTermsOfUse;
        motivation = builder.motivation;
        accountCreated = builder.accountCreated;
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

    public boolean isAcceptTermsOfUse() {
        return acceptTermsOfUse;
    }

    public String getMotivation() {
        return motivation;
    }

    public boolean isAccountCreated() {
        return accountCreated;
    }

    /**
     * Accept the terms of use
     */
    public void acceptTermsOfUse(){
        acceptTermsOfUse = true;
    }

    /**
     * Update the status of the author
     * @param status the new status
     */
    public void updateStatus(AuthorStatusEnum status){
        this.status = status;
    }

    /**
     * Indicate that account is created in Keycloak
     */
    public void createAccount(){
        accountCreated = true;
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
        private boolean acceptTermsOfUse;
        private String motivation;
        private boolean accountCreated;

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

        public Builder withAcceptTermsOfUse(boolean val) {
            this.acceptTermsOfUse = val;
            return this;
        }

        public Builder withMotivation(String val) {
            this.motivation = val;
            return this;
        }

        public Builder withAccountCreated(boolean val) {
            this.accountCreated = val;
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
