package be.unamur.fpgen.web;

import be.unamur.api.AuthorApi;
import be.unamur.fpgen.mapper.domainToWeb.AuthorDomainToWebMapper;
import be.unamur.fpgen.mapper.domainToWeb.pagination.AuthorPaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.AuthorWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.pagination.AuthorPaginationWebToDomainMapper;
import be.unamur.fpgen.service.AuthorService;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.Author;
import be.unamur.model.AuthorCreation;
import be.unamur.model.AuthorsPage;
import be.unamur.model.PagedAuthorQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
public class AuthorController implements AuthorApi {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public ResponseEntity<Author> createAuthor(@Valid AuthorCreation authorCreation) {
        final Author author = AuthorDomainToWebMapper.map(authorService.createIfNotExists(AuthorWebToDomainMapper.map(authorCreation)));
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Author> getAuthorById(UUID authorId) {
        return new ResponseEntity<>(AuthorDomainToWebMapper.map(authorService.getAuthorById(authorId)), HttpStatus.OK);
    }

    @RolesAllowed({"user"})
    @Override
    public ResponseEntity<AuthorsPage> searchAuthorsPaginate(@Valid PagedAuthorQuery pagedAuthorQuery) {
        return new ResponseEntity<>(
                AuthorPaginationDomainToWebMapper.map(authorService.searchAuthorPaginate(
                        AuthorPaginationWebToDomainMapper.map(pagedAuthorQuery)
                )
                ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Author>> getAuthorList() {
        return new ResponseEntity<>(MapperUtil.mapList(authorService.getAuthors(), AuthorDomainToWebMapper::map), HttpStatus.OK);
    }
}
