package be.unamur.fpgen.service;

import be.unamur.fpgen.author.pagination.AuthorsPage;
import be.unamur.fpgen.author.pagination.PagedAuthorsQuery;
import be.unamur.fpgen.exception.AuthorNotFoundException;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.repository.AuthorRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Transactional
    public Author create(final Author author){
        return authorRepository.saveAuthor(author);
    }

    @Transactional
    public Author createIfNotExists(final Author author){
        if(authorRepository.existsAuthorByTrigram(author.getTrigram())){
            throw AuthorNotFoundException.withTrigram(author.getTrigram());
        }
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
}
