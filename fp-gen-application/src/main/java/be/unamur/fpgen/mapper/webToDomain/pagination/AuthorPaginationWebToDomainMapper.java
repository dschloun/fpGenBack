package be.unamur.fpgen.mapper.webToDomain.pagination;

import be.unamur.fpgen.author.pagination.AuthorQuery;
import be.unamur.fpgen.author.pagination.PagedAuthorsQuery;
import be.unamur.fpgen.mapper.webToDomain.AuthorWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.PaginationWebToDomainMapper;
import be.unamur.model.PagedAuthorQuery;

import java.util.Optional;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class AuthorPaginationWebToDomainMapper {
    public static AuthorQuery map(be.unamur.model.AuthorQuery web) {
        return AuthorQuery.newBuilder()
                .withFirstname(web.getFirstname())
                .withLastname(web.getLastname())
                .withOrganization(web.getOrganization())
                .withFunction(web.getAuthorFunction())
                .withTrigram(web.getTrigram())
                .withEmail(web.getEmail())
                .withStatus(AuthorWebToDomainMapper.map(web.getStatus()))
                .build();
    }

    public static PagedAuthorsQuery map(PagedAuthorQuery web) {
        return PagedAuthorsQuery.newBuilder()
                .withAuthorQuery(map(web.getQuery()))
                .withQueryPage(PaginationWebToDomainMapper.map(web.getPage()))
                .build();
    }
}
