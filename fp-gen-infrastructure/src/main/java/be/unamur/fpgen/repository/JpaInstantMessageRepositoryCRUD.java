package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaInstantMessageRepositoryCRUD extends JpaRepository<InstantMessageEntity, UUID> {
}
