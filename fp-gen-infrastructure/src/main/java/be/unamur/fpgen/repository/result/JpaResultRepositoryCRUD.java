package be.unamur.fpgen.repository.result;

import be.unamur.fpgen.entity.result.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaResultRepositoryCRUD extends JpaRepository<ResultEntity, UUID> {
}
