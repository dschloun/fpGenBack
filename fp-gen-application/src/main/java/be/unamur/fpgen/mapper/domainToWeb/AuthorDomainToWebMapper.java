package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.model.Author;
import be.unamur.model.AuthorStatusEnum;

import java.util.Optional;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class AuthorDomainToWebMapper {
    public static Author map(be.unamur.fpgen.author.Author domain) {
        return new Author().id(domain.getId())
                .lastname(domain.getLastName())
                .firstname(domain.getFirstName())
                .trigram(domain.getTrigram())
                .organization(domain.getOrganization())
                .authorFunction(domain.getFunction())
                .email(domain.getEmail())
                .phoneNumber(domain.getPhoneNumber())
                .status(map(domain.getStatus()))
                .acceptTermsOfUse(domain.isAcceptTermsOfUse())
                .motivation(domain.getMotivation());
    }

    public static AuthorStatusEnum map(final be.unamur.fpgen.author.AuthorStatusEnum domain) {
        return Optional.ofNullable(domain)
                .map(e -> AuthorStatusEnum.fromValue(e.name()))
                .orElse(null);
    }
}
