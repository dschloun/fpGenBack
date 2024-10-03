package be.unamur.fpgen.mapper.webToDomain.pagination;

import be.unamur.fpgen.author.pagination.AuthorQuery;
import be.unamur.fpgen.author.pagination.PagedAuthorsQuery;
import be.unamur.fpgen.mapper.webToDomain.PaginationWebToDomainMapper;
import be.unamur.model.PagedAuthorQuery;

import java.util.Optional;

public class AuthorPaginationWebToDomainMapper {
    public static AuthorQuery map(be.unamur.model.AuthorQuery web) {
        return AuthorQuery.newBuilder()
                .withFirstname(web.getFirstname())
                .withLastname(web.getLastname())
                .withOrganization(web.getOrganization())
                .withFunction(web.getAuthorFunction())
                .withTrigram(web.getTrigram())
                .withEmail(web.getEmail())
                .withStatus(Optional.ofNullable(web.getStatus()).map(Enum::name).orElse(null))
                .build();
    }

    public static PagedAuthorsQuery map(PagedAuthorQuery web) {
        return PagedAuthorsQuery.newBuilder()
                .withAuthorQuery(map(web.getQuery()))
                .withQueryPage(PaginationWebToDomainMapper.map(web.getPage()))
                .build();
    }
}
