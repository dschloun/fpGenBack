package be.unamur.fpgen.repository;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.author.pagination.AuthorsPage;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository {

    Author saveAuthor(Author author);

    Author updateAuthor(Author author);

    Optional<Author> getAuthorById(UUID authorId);

    Optional<Author> findAuthorByTrigram(String trigram);

    boolean existsAuthorByTrigram(String trigram);

    AuthorsPage findAuthorsPagination(String lastName, String firstName, String organization, String function, String trigram, String email, Pageable pageable);
}
