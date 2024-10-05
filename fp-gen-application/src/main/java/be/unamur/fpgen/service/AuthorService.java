package be.unamur.fpgen.service;

import be.unamur.fpgen.author.AuthorStatusEnum;
import be.unamur.fpgen.author.pagination.AuthorsPage;
import be.unamur.fpgen.author.pagination.PagedAuthorsQuery;
import be.unamur.fpgen.exception.AuthorAlreadyExistException;
import be.unamur.fpgen.exception.AuthorNotFoundException;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.repository.AuthorRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final KeycloakService keycloakService;

    public AuthorService(AuthorRepository authorRepository, KeycloakService keycloakService) {
        this.authorRepository = authorRepository;
        this.keycloakService = keycloakService;
    }


    @Transactional
    public Author create(final Author author){
        return authorRepository.saveAuthor(author);
    }

    @Transactional
    public Author createIfNotExists(final Author author){
        if(authorRepository.existsAuthorByTrigram(author.getTrigram())){
            throw AuthorAlreadyExistException.withTrigram(author.getTrigram());
        }
        author.updateStatus(AuthorStatusEnum.WAITING_VERIFICATION);
        return authorRepository.saveAuthor(author);
    }

    @Transactional
    public Author getAuthorById(final UUID authorId){
        return authorRepository.getAuthorById(authorId).orElseThrow(() -> AuthorNotFoundException.withId(authorId));
    }

    @Transactional
    public Author getAuthorByTrigram(final String trigram){
        return authorRepository.findAuthorByTrigram(trigram).orElseThrow(() -> AuthorNotFoundException.withTrigram(trigram));
    }

    @Transactional
    public List<Author> getAuthors(){
        return authorRepository.getAuthors();
    }

    @Transactional
    public AuthorsPage searchAuthorPaginate(final PagedAuthorsQuery query){
        //1. get pageable
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());

        //2. search Authors
        return authorRepository.findAuthorsPagination(
                query.getAuthorQuery().getLastname(),
                query.getAuthorQuery().getFirstname(),
                query.getAuthorQuery().getOrganization(),
                query.getAuthorQuery().getFunction(),
                query.getAuthorQuery().getTrigram(),
                query.getAuthorQuery().getEmail(),
                pageable);
    }

    @Transactional
    public void updateAuthorStatus(final UUID authorId, final AuthorStatusEnum status){
        // 1. get author
        final Author author = authorRepository.getAuthorById(authorId).orElseThrow(() -> AuthorNotFoundException.withId(authorId));

        // 2. create user keycloak
        if (AuthorStatusEnum.VERIFIED.equals(status)) {
            keycloakService.createUser(author);
        }

        // 3. update author status
        author.updateStatus(status);
        authorRepository.updateAuthor(author);
    }
}
