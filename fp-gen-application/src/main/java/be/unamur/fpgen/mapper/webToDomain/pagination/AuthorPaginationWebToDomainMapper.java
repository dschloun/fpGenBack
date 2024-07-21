package be.unamur.fpgen.mapper.webToDomain.pagination;

import be.unamur.fpgen.author.pagination.AuthorQuery;
import be.unamur.fpgen.author.pagination.PagedAuthorsQuery;
import be.unamur.fpgen.mapper.webToDomain.PaginationWebToDomainMapper;
import be.unamur.fpgen.message.pagination.conversation_message.PagedConversationMessagesQuery;
import be.unamur.model.PagedAuthorQuery;
import be.unamur.model.PagedConversationMessageQuery;

public class AuthorPaginationWebToDomainMapper {
    public static AuthorQuery map(be.unamur.model.AuthorQuery web) {
        return AuthorQuery.newBuilder()
                .withFirstname(web.getFirstname())
                .withLastname(web.getLastname())
                .withOrganisation(web.getOrganisation())
                .withFunction(web.getFunction())
                .withTrigram(web.getTrigram())
                .withDescription(web.getDescription())
                .withComment(web.getComment())
                .withAuthorTrigram(web.getAuthorTrigram())
                .build();
    }

    public static PagedAuthorsQuery map(PagedAuthorQuery web) {
        return PagedAuthorsQuery.newBuilder()
                .withAuthorQuery(map(web.getQuery()))
                .withQueryPage(PaginationWebToDomainMapper.map(web.getPage()))
                .build();
    }
}
