package be.unamur.fpgen.repository;

import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;
import be.unamur.fpgen.instant_message.InstantMessage;
import be.unamur.fpgen.mapper.domainToJpa.InstantMessageDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InstantMessageJpaToDomainMapper;
import liquibase.repackaged.org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaInstantMessageRepository implements InstantMessageRepository {

    private final JpaInstantMessageRepositoryCRUD jpaInstantMessageRepositoryCRUD;

    public JpaInstantMessageRepository(JpaInstantMessageRepositoryCRUD jpaInstantMessageRepositoryCRUD) {
        this.jpaInstantMessageRepositoryCRUD = jpaInstantMessageRepositoryCRUD;
    }

    @Override
    public List<InstantMessage> saveInstantMessageList(List<InstantMessage> instantMessage) {
        List<InstantMessageEntity> l = jpaInstantMessageRepositoryCRUD.saveAll(ListUtils.emptyIfNull(instantMessage)
                .stream()
                .map(InstantMessageDomainToJpaMapper::mapForCreate)
                .toList());

        return l.stream()
                .map(InstantMessageJpaToDomainMapper::map)
                .toList();
    }
}
