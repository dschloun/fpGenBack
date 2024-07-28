package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.model.Author;

public class AuthorDomainToWebMapper {
    public static Author map(be.unamur.fpgen.author.Author domain) {
        return new Author().id(domain.getId())
                .lastname(domain.getLastName())
                .firstname(domain.getFirstName())
                .trigram(domain.getTrigram())
                .organization(domain.getOrganization())
                .authorFunction(domain.getFunction())
                .email(domain.getEmail())
                .phoneNumber(domain.getPhoneNumber());
    }
}
