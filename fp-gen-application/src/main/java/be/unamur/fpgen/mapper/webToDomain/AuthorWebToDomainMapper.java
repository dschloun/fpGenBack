package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.author.AuthorStatusEnum;
import be.unamur.model.AuthorCreation;

import java.util.Optional;

public class AuthorWebToDomainMapper {

    public static Author map(AuthorCreation authorCreation){
        return Author.newBuilder()
                .withLastName(authorCreation.getLastname())
                .withFirstName(authorCreation.getFirstname())
                .withTrigram(authorCreation.getTrigram())
                .withOrganization(authorCreation.getOrganization())
                .withFunction(authorCreation.getAuthorFunction())
                .withEmail(authorCreation.getEmail())
                .withPhoneNumber(authorCreation.getPhoneNumber())
                .build();
    }

    public static AuthorStatusEnum map(final be.unamur.model.AuthorStatusEnum web){
        return Optional.ofNullable(web)
                .map(status -> AuthorStatusEnum.valueOf(status.name()))
                .orElse(null);
    }
}
