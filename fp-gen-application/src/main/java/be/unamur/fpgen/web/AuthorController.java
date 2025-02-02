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

/**
 * This rest controller class is the implementation of the AuthorApi interface.
 * It is used to manage the authors.
 */
@Controller
public class AuthorController implements AuthorApi {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * This method is used to create an author.
     * @param authorCreation the author to create
     * @return the created author
     */
    @Override
    public ResponseEntity<Author> createAuthor(@Valid AuthorCreation authorCreation) {
        final Author author = AuthorDomainToWebMapper.map(authorService.createIfNotExists(AuthorWebToDomainMapper.map(authorCreation)));
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    /**
     * This method is used to get an author by its id.
     * @param authorId
     * @return the author
     */
    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<Author> getAuthorById(UUID authorId) {
        return new ResponseEntity<>(AuthorDomainToWebMapper.map(authorService.getAuthorById(authorId)), HttpStatus.OK);
    }

    /**
     * paginate search authors
     * @param pagedAuthorQuery
     * @return the authors page
     */
    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<AuthorsPage> searchAuthorsPaginate(@Valid PagedAuthorQuery pagedAuthorQuery) {
        return new ResponseEntity<>(
                AuthorPaginationDomainToWebMapper.map(authorService.searchAuthorPaginate(
                        AuthorPaginationWebToDomainMapper.map(pagedAuthorQuery)
                )
                ), HttpStatus.OK);
    }

    /**
     * This method is used to get the list of authors.
     * @return the list of authors
     */
    @RolesAllowed({"administrator"})
    @Override
    public ResponseEntity<List<Author>> getAuthorList() {
        return new ResponseEntity<>(MapperUtil.mapList(authorService.getAuthors(), AuthorDomainToWebMapper::map), HttpStatus.OK);
    }
}
