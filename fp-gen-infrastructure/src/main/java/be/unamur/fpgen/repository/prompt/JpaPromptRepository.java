package be.unamur.fpgen.repository.prompt;

import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.entity.PromptEntity;
import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.mapper.domainToJpa.PromptDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.PromptJpaToDomainMapper;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.prompt.Prompt;
import be.unamur.fpgen.prompt.PromptStatusEnum;
import be.unamur.fpgen.repository.ProjectRepository;
import be.unamur.fpgen.repository.PromptRepository;
import be.unamur.fpgen.repository.author.JpaAuthorRepositoryCRUD;
import be.unamur.fpgen.utils.MapperUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * See the specifications in the {@link PromptRepository} interface.
 */
@Repository
public class JpaPromptRepository implements PromptRepository {
    private final JpaPromptRepositoryCRUD jpaPromptRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;

    public JpaPromptRepository(JpaPromptRepositoryCRUD jpaPromptRepositoryCRUD, JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD) {
        this.jpaPromptRepositoryCRUD = jpaPromptRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
    }

    @Override
    public Optional<Prompt> findPromptBId(UUID id) {
        return jpaPromptRepositoryCRUD.findById(id).map(PromptJpaToDomainMapper::map);
    }

    @Override
    public Optional<Prompt> findPromptByDatasetTypeAndMessageTypeAndVersion(DatasetTypeEnum datasetType, MessageTypeEnum messageType, Integer version) {
        return jpaPromptRepositoryCRUD.findByDatasetTypeAndMessageTypeAndVersion(datasetType, messageType, version).map(PromptJpaToDomainMapper::map);
    }

    @Override
    public void updatePromptStatus(UUID id, PromptStatusEnum status) {
        jpaPromptRepositoryCRUD.findById(id).ifPresent(entity -> {
            entity.setStatus(status);
            jpaPromptRepositoryCRUD.save(entity);
        });
    }

    @Override
    public void setDefaultPrompt(UUID id) {
        final Optional<PromptEntity> promptEntity = jpaPromptRepositoryCRUD.findById(id);
        promptEntity.ifPresent(p-> {
            jpaPromptRepositoryCRUD.findByDatasetTypeAndMessageTypeAndDefaultPromptIsTrue(p.getDatasetType(), p.getMessageType()).ifPresent(entity -> {
                entity.setDefaultPrompt(false);
                jpaPromptRepositoryCRUD.save(entity);
            });
            p.setDefaultPrompt(true);
            jpaPromptRepositoryCRUD.save(p);
        });
    }

    @Override
    public Optional<Prompt> getDefaultPrompt(DatasetTypeEnum datasetType, MessageTypeEnum messageType) {
        return jpaPromptRepositoryCRUD.findByDatasetTypeAndMessageTypeAndDefaultPromptIsTrue(datasetType, messageType).map(PromptJpaToDomainMapper::map);
    }

    @Override
    public List<Prompt> findAllByDatasetTypeAndMessageType(DatasetTypeEnum datasetType, MessageTypeEnum messageType, PromptStatusEnum status) {
        return MapperUtil.mapList(jpaPromptRepositoryCRUD.findAllByDatasetTypeAndMessageTypeAndStatusOrderByVersionAsc(datasetType, messageType, status), PromptJpaToDomainMapper::map);
    }

    @Override
    public List<Prompt> findAllPromptsByStatus(PromptStatusEnum status) {
        return MapperUtil.mapList(jpaPromptRepositoryCRUD.findAllByStatusOrderByVersionAsc( status), PromptJpaToDomainMapper::map);
    }

    @Override
    public Prompt savePrompt(Prompt prompt) {
        final AuthorEntity authorEntity = jpaAuthorRepositoryCRUD.getReferenceById(prompt.getAuthor().getId());
        return PromptJpaToDomainMapper.map(jpaPromptRepositoryCRUD.save(PromptDomainToJpaMapper.mapForCreate(prompt, authorEntity)));
    }

    @Override
    public void updatePrompt(Prompt prompt) {
        jpaPromptRepositoryCRUD.findById(prompt.getId()).ifPresent(entity -> {
            entity.setUserPrompt(prompt.getUserPrompt());
            entity.setSystemPrompt(prompt.getSystemPrompt());
            jpaPromptRepositoryCRUD.save(entity);
        });
    }

    @Override
    public Integer findMaxVersionByDatasetTypeAndMessageType(DatasetTypeEnum datasetType, MessageTypeEnum messageType) {
        return jpaPromptRepositoryCRUD.findMaxVersionByDatasetTypeAndMessageType(datasetType, messageType);
    }
}
