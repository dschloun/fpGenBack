package be.unamur.fpgen.service.identification;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.context.UserContextHolder;
import be.unamur.fpgen.exception.AuthorUnauthorizedException;
import be.unamur.fpgen.service.FindByIdService;

import java.util.UUID;

public class AuthorVerification {

    private final FindByIdService findByIdService;

    private AuthorVerification(Builder builder) {
        findByIdService = builder.findByIdService;
    }

    public FindByIdService getFindByIdService() {
        return findByIdService;
    }

    public void verifyAuthor(UUID resourceId){
        final String authorEmail = UserContextHolder.getContext().getEmail();
        final Author initial_author = findByIdService.findById(resourceId).getAuthor();
        if(!initial_author.getEmail().equals(authorEmail)){
            throw AuthorUnauthorizedException.withEmail(authorEmail);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private FindByIdService findByIdService;

        private Builder() {
        }

        public Builder withFindByIdService(FindByIdService val) {
            findByIdService = val;
            return this;
        }

        public AuthorVerification build() {
            return new AuthorVerification(this);
        }
    }
}
