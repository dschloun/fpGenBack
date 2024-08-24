package be.unamur.fpgen.repository.dataset;

import be.unamur.fpgen.entity.dataset.InstantMessageDatasetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaInstantMessageDatasetRepositoryCRUD extends JpaRepository<InstantMessageDatasetEntity, UUID>{
}
