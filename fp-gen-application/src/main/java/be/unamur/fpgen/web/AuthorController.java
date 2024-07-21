package be.unamur.fpgen.web;

import be.unamur.api.AuthorApi;
import be.unamur.fpgen.mapper.domainToWeb.AuthorDomainToWebMapper;
import be.unamur.fpgen.mapper.domainToWeb.pagination.AuthorPaginationDomainToWebMapper;
import be.unamur.fpgen.mapper.webToDomain.AuthorWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.pagination.AuthorPaginationWebToDomainMapper;
import be.unamur.fpgen.service.AuthorService;
import be.unamur.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
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

    @Override
    public ResponseEntity<Author> getAuthorById(UUID authorId) {
        return new ResponseEntity<>(AuthorDomainToWebMapper.map(authorService.getAuthorById(authorId)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthorsPage> searchAuthorsPaginate(@Valid PagedAuthorQuery pagedAuthorQuery) {
        return new ResponseEntity<>(
                AuthorPaginationDomainToWebMapper.map(authorService.searchAuthorPaginate(
                        AuthorPaginationWebToDomainMapper.map(pagedAuthorQuery)
                )
                ), HttpStatus.OK);
    }
}
