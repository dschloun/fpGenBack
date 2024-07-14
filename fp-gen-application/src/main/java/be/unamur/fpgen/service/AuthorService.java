package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Author createIfNotExists(final Author author){
        return authorRepository.getAuthorById(author.getId())
                .orElseGet(() -> {
                    return authorRepository.saveAuthor(author);
                });
    }
}
