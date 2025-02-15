package be.unamur.fpgen.repository.author;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.author.AuthorStatusEnum;
import be.unamur.fpgen.author.pagination.AuthorsPage;
import be.unamur.fpgen.mapper.domainToJpa.AuthorDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.AuthorJpaToDomainMapper;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.repository.AuthorRepository;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.fpgen.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * See the specifications in the {@link AuthorRepository} interface.
 */
@Repository
public class JpaAuthorRepository implements AuthorRepository {

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
    public void updateAuthor(Author author) {
        jpaAuthorRepositoryCRUD.findById(author.getId())
                .ifPresent(a -> {
                    a.setStatus(author.getStatus());
                    a.setAccountCreated(author.isAccountCreated());
                    jpaAuthorRepositoryCRUD.save(a);
                });
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

    @Override
    public List<Author> getAuthors() {
        return jpaAuthorRepositoryCRUD.findAllByOrderByTrigramAsc()
                .stream()
                .map(AuthorJpaToDomainMapper::map)
                .toList();
    }

    @Override
    public AuthorsPage findAuthorsPagination(String lastName, String firstName, String organization, String function, String trigram, String email, AuthorStatusEnum status, Pageable pageable) {
        // 1. get in Page format
        Page<Author> page = jpaAuthorRepositoryCRUD.findPagination(
                StringUtil.toLowerCaseIfNotNull(lastName),
                StringUtil.toLowerCaseIfNotNull(firstName),
                StringUtil.toLowerCaseIfNotNull(organization),
                StringUtil.toLowerCaseIfNotNull(function),
                StringUtil.toLowerCaseIfNotNull(trigram),
                StringUtil.toLowerCaseIfNotNull(email),
                status,
                pageable
        ).map(AuthorJpaToDomainMapper::map);

        // 2. convert
        final AuthorsPage authorsPage = AuthorsPage.newBuilder()
                .withPagination(new Pagination.Builder()
                        .size(page.getSize())
                        .totalSize((int) page.getTotalElements())
                        .page(page.getNumber())
                        .build())
                .withAuthorList(page.getContent())
                .build();

        // 3. return
        return authorsPage;
    }

    @Override
    public List<Author> findAdministrators() {
        return MapperUtil.mapList(jpaAuthorRepositoryCRUD.findAllByAdministratorIsTrue(), AuthorJpaToDomainMapper::map);
    }
}
