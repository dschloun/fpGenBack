package be.unamur.fpgen.repository.interclocutor;

import be.unamur.fpgen.entity.interlocutor.InterlocutorEntity;
import be.unamur.fpgen.interlocutor.Interlocutor;
import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaInterlocutorRepositoryCRUD extends JpaRepository<InterlocutorEntity, Integer>{

    List<InterlocutorEntity> findByInterlocutorTypeEnum(final InterlocutorTypeEnum type);

    Optional<InterlocutorEntity> getInterlocutorEntityByInterlocutorTypeEnum(final InterlocutorTypeEnum type);

    Optional<InterlocutorEntity> getInterlocutorEntityByInterlocutorTypeEnumAndNumber(InterlocutorTypeEnum type, int number);
}
