package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.entity.author.AuthorEntity;

import java.util.Objects;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class AuthorJpaToDomainMapper {

    public static Author map(final AuthorEntity entity){
        if (Objects.isNull(entity)){
            return null;
        }

        return Author.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withLastName(entity.getLastname())
                .withFirstName(entity.getFirstname())
                .withTrigram(entity.getTrigram())
                .withOrganization(entity.getOrganization())
                .withFunction(entity.getFunction())
                .withEmail(entity.getEmail())
                .withPhoneNumber(entity.getPhoneNumber())
                .withStatus(entity.getStatus())
                .withAcceptTermsOfUse(entity.isAcceptTermsOfUse())
                .withMotivation(entity.getMotivation())
                .withAccountCreated(entity.isAccountCreated())
                .build();
    }
}
