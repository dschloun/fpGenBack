package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.interlocutor.InterlocutorEntity;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaInterlocutorRepositoryCRUD extends JpaRepository<InterlocutorEntity, Long>{

    List<InterlocutorEntity> findByInterlocutorTypeEnum(final InterlocutorTypeEnum type);
}
