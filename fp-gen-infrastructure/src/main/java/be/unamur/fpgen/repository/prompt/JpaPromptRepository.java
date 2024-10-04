package be.unamur.fpgen.repository.prompt;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.mapper.domainToJpa.PromptDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.PromptJpaToDomainMapper;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.prompt.Prompt;
import be.unamur.fpgen.prompt.PromptStatusEnum;
import be.unamur.fpgen.repository.PromptRepository;
import be.unamur.fpgen.repository.author.JpaAuthorRepositoryCRUD;
import be.unamur.fpgen.utils.MapperUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<Prompt> findPromptByVersion(Integer version) {
        return jpaPromptRepositoryCRUD.findByVersion(version).map(PromptJpaToDomainMapper::map);
    }

    @Override
    public void updatePromptStatus(UUID id, PromptStatusEnum status) {
        jpaPromptRepositoryCRUD.findById(id).ifPresent(entity -> {
            entity.setStatus(status);
            jpaPromptRepositoryCRUD.save(entity);
        });
    }

    @Override
    public void setDefaultPrompt(UUID id, boolean state) {
        jpaPromptRepositoryCRUD.findById(id).ifPresent(entity -> {
            entity.setDefaultPrompt(state);
            jpaPromptRepositoryCRUD.save(entity);
        });
    }

    @Override
    public Optional<Prompt> getDefaultPrompt() {
        return jpaPromptRepositoryCRUD.findByDefaultPromptIsTrue().map(PromptJpaToDomainMapper::map);
    }

    @Override
    public List<Prompt> findAllPromptsByType(MessageTypeEnum type, PromptStatusEnum status) {
        return MapperUtil.mapList(jpaPromptRepositoryCRUD.findAllByTypeAndStatusEquals(type, status), PromptJpaToDomainMapper::map);
    }

    @Override
    public List<Prompt> findAllPromptsByStatus(PromptStatusEnum status) {
        return MapperUtil.mapList(jpaPromptRepositoryCRUD.findAllByStatus( status), PromptJpaToDomainMapper::map);
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
}
