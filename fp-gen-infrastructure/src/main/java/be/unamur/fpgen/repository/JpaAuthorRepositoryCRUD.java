package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.author.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaAuthorRepositoryCRUD extends JpaRepository<AuthorEntity, UUID> {

    Optional<AuthorEntity> findByTrigram(String trigram);
}
