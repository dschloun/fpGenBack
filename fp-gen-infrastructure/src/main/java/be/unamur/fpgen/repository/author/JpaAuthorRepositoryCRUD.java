package be.unamur.fpgen.repository.author;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.author.AuthorStatusEnum;
import be.unamur.fpgen.entity.author.AuthorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaAuthorRepositoryCRUD extends JpaRepository<AuthorEntity, UUID> {

    Optional<AuthorEntity> findByTrigram(String trigram);

    List<AuthorEntity> findAllByOrderByTrigramAsc();

    boolean existsByTrigram(String trigram);

    @Query(value = "SELECT DISTINCT i from AuthorEntity i" +
            " WHERE (:lastname is null or lower(i.lastname) like %:lastname%)" +
            " AND (:firstname is null or lower(i.firstname) like %:firstname%)" +
            " AND (:organization is null or lower(i.organization) like %:organization%)" +
            " AND (:function is null or lower(i.function) like %:function%)" +
            " AND (:trigram is null or lower(i.trigram) like %:trigram%)" +
            " AND (:email is null or lower(i.email) like %:email%)" +
            " AND (:status is null or i.status = :status) "
    )
    Page<AuthorEntity> findPagination(@Param("lastname") String lastname,
                                      @Param("firstname") String firstname,
                                      @Param("organization") String organization,
                                      @Param("function") String function,
                                      @Param("trigram") String trigram,
                                      @Param("email") String email,
                                      @Param("status") AuthorStatusEnum status,
                                      Pageable pageable);

    List<AuthorEntity> findAllByAdministratorIsTrue();
}
