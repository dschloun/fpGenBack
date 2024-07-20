package be.unamur.fpgen.service;

import be.unamur.fpgen.exception.AuthorNotFoundException;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.repository.AuthorRepository;
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
        return authorRepository.getAuthorById(author.getId())
                .orElseGet(() -> authorRepository.saveAuthor(author));
    }

    @Transactional
    public Author getAuthorById(final UUID authorId){
        return authorRepository.getAuthorById(authorId).orElseThrow(() -> AuthorNotFoundException.withId(authorId));
    }

    @Transactional
    public Author getAuthorByTrigram(final String trigram){
        return authorRepository.findAuthorByTrigram(trigram).orElseThrow(() -> AuthorNotFoundException.withTrigram(trigram));
    }
}
