package be.unamur.fpgen.repository;

import be.unamur.fpgen.author.Author;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository {

    Author saveAuthor(Author author);

    Author updateAuthor(Author author);

    Optional<Author> getAuthorById(UUID authorId);

    Optional<Author> findAuthorByTrigram(String trigram);
}
