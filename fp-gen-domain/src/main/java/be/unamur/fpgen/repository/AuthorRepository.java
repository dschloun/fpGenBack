package be.unamur.fpgen.repository;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.author.AuthorStatusEnum;
import be.unamur.fpgen.author.pagination.AuthorsPage;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository {

    Author saveAuthor(Author author);

    void updateAuthor(Author author);

    Optional<Author> getAuthorById(UUID authorId);

    Optional<Author> findAuthorByTrigram(String trigram);

    boolean existsAuthorByTrigram(String trigram);

    List<Author> getAuthors();
    AuthorsPage findAuthorsPagination(String lastName, String firstName, String organization, String function, String trigram, String email, AuthorStatusEnum status, Pageable pageable);
}
