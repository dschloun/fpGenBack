package be.unamur.fpgen.repository;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.author.AuthorStatusEnum;
import be.unamur.fpgen.author.pagination.AuthorsPage;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for authors.
 *
 */
public interface AuthorRepository {

    /**
     * Save an author.
     * @param author the author to save
     * @return the saved author
     */
    Author saveAuthor(Author author);

    /**
     * Update an author.
     * @param author the author to update
     */
    void updateAuthor(Author author);

    /**
     * Delete an author.
     * @param the authorId to delete
     */
    Optional<Author> getAuthorById(UUID authorId);

    /**
     * Find an author by trigram.
     * @param trigram the trigram to search
     * @return the author if found, empty otherwise
     */
    Optional<Author> findAuthorByTrigram(String trigram);

    /**
     * Check if an author exists by trigram.
     * @param trigram the trigram to search
     * @return true if the author exists, false otherwise
     */
    boolean existsAuthorByTrigram(String trigram);

    /**
     * Find all authors.
     * @return the author list
     * */
    List<Author> getAuthors();

    /**
     * Find all authors with different filter criteria
     * @param lastName
     * @param firstName
     * @param organization
     * @param function
     * @param trigram
     * @param email
     * @param status
     * @param pageable
     * @return a paginate search result
     */
    AuthorsPage findAuthorsPagination(String lastName, String firstName, String organization, String function, String trigram, String email, AuthorStatusEnum status, Pageable pageable);

    /**
     * Find all administrator
     * @return the list of administrators
     */
    List<Author> findAdministrators();

}
