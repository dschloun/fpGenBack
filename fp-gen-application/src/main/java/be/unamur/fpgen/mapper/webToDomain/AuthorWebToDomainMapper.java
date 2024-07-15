package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.author.Author;
import be.unamur.model.AuthorCreation;

public class AuthorWebToDomainMapper {

    public static Author map(AuthorCreation authorCreation){
        return Author.newBuilder()
                .withLastName(authorCreation.getLastName())
                .withFirstName(authorCreation.getFirstName())
                .withTrigram(authorCreation.getTrigram())
                .withOrganization(authorCreation.getOrganization())
                .withFunction(authorCreation.getFunction())
                .withEmail(authorCreation.getEmail())
                .withPhoneNumber(authorCreation.getPhoneNumber())
                .build();
    }
}
