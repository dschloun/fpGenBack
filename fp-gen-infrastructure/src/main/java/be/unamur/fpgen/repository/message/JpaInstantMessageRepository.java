package be.unamur.fpgen.repository.message;

import be.unamur.fpgen.entity.generation.InstantMessageGenerationEntity;
import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.message.InstantMessage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.message.pagination.InstantMessage.InstantMessagesPage;
import be.unamur.fpgen.mapper.domainToJpa.InstantMessageDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InstantMessageJpaToDomainMapper;
import be.unamur.fpgen.pagination.Pagination;
import be.unamur.fpgen.repository.InterlocutorRepository;
import be.unamur.fpgen.repository.MessageRepository;
import be.unamur.fpgen.repository.generation.JpaInstantMessageGenerationRepositoryCRUD;
import be.unamur.fpgen.utils.StringUtil;
import liquibase.repackaged.org.apache.commons.collections4.ListUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * See the specifications in the {@link MessageRepository} interface.
 */
@Repository
public class JpaInstantMessageRepository implements MessageRepository {

    private final JpaInstantMessageRepositoryCRUD jpaInstantMessageRepositoryCRUD;
    private final JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD;

    public JpaInstantMessageRepository(final JpaInstantMessageRepositoryCRUD jpaInstantMessageRepositoryCRUD,
                                       JpaInstantMessageGenerationRepositoryCRUD jpaInstantMessageGenerationRepositoryCRUD) {
        this.jpaInstantMessageGenerationRepositoryCRUD = jpaInstantMessageGenerationRepositoryCRUD;
        this.jpaInstantMessageRepositoryCRUD = jpaInstantMessageRepositoryCRUD;

    }

    @Override
    public List<InstantMessage> saveInstantMessageList(List<InstantMessage> instantMessageList, Generation instantMessageGeneration) {
        final InstantMessageGenerationEntity generationEntity = jpaInstantMessageGenerationRepositoryCRUD.findById(instantMessageGeneration.getId()).orElseThrow();
        List<InstantMessageEntity> l = jpaInstantMessageRepositoryCRUD.saveAll(ListUtils.emptyIfNull(instantMessageList)
                .stream()
                .map(i -> InstantMessageDomainToJpaMapper.mapForCreate(i, generationEntity))
                .toList());

        return l.stream()
                .map(InstantMessageJpaToDomainMapper::map)
                .toList();
    }

    @Override
    public void deleteInstantMessageById(UUID instantMessageId) {
        jpaInstantMessageRepositoryCRUD.deleteById(instantMessageId);
    }

    @Override
    public Optional<InstantMessage> getInstantMessageById(UUID instantMessageId) {
        return jpaInstantMessageRepositoryCRUD.findById(instantMessageId)
                .map(InstantMessageJpaToDomainMapper::map);
    }

    @Override
    public InstantMessagesPage findPagination(MessageTopicEnum topic, MessageTypeEnum type, String content, OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable) {
        // 1. get in Page format
        Page<InstantMessage> page = jpaInstantMessageRepositoryCRUD
                .findPagination(
                        topic,
                        type,
                        StringUtil.toLowerCaseIfNotNull(content),
                        startDate,
                        endDate,
                        pageable
                ).map(InstantMessageJpaToDomainMapper::map);

        // 2. convert
        final InstantMessagesPage instantMessagesPage = InstantMessagesPage.newBuilder()
                .withPagination(new Pagination.Builder()
                        .size(page.getSize())
                        .totalSize((int) page.getTotalElements())
                        .page(page.getNumber())
                        .build())
                .withInstantMessageList(page.getContent())
                .build();

        // 3. return
        return instantMessagesPage;
    }

    @Override
    public List<InstantMessage> findInstantMessageByGenerationId(UUID generationId) {
        return jpaInstantMessageRepositoryCRUD.findAllByInstantMessageGenerationId(generationId)
                .stream()
                .map(InstantMessageJpaToDomainMapper::map)
                .toList();
    }

    @Override
    public boolean existByHash(String hash) {
        return jpaInstantMessageRepositoryCRUD.existsByHash(hash);
    }
}