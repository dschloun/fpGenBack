package be.unamur.fpgen.repository;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.mapper.domainToJpa.AuthorDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.AuthorJpaToDomainMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaAuthorRepository implements AuthorRepository{

    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;

    public JpaAuthorRepository(JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD) {
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
    }

    @Override
    public Author saveAuthor(Author author) {
        return AuthorJpaToDomainMapper.map(
                jpaAuthorRepositoryCRUD.save(AuthorDomainToJpaMapper.mapForCreate(author))
        );
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public Optional<Author> getAuthorById(UUID authorId) {
        return jpaAuthorRepositoryCRUD.findById(authorId).map(AuthorJpaToDomainMapper::map);
    }

    @Override
    public Optional<Author> findAuthorByTrigram(String trigram) {
        return jpaAuthorRepositoryCRUD.findByTrigram(trigram).map(AuthorJpaToDomainMapper::map);
    }

    @Override
    public boolean existsAuthorByTrigram(String trigram) {
        return jpaAuthorRepositoryCRUD.existsByTrigram(trigram);
    }
}
