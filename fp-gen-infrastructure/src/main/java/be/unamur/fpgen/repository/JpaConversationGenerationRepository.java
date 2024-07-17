package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.mapper.domainToJpa.ConversationGenerationDomainToJpaMapper;
import be.unamur.fpgen.mapper.domainToJpa.InstantMessageGenerationDomainToJpaMapper;
import be.unamur.fpgen.mapper.jpaToDomain.ConversationGenerationJpaToDomainMapper;
import be.unamur.fpgen.mapper.jpaToDomain.InstantMessageGenerationJpaToDomainMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaConversationGenerationRepository implements ConversationGenerationRepository{
    private final JpaConversationGenerationRepositoryCRUD jpaConversationGenerationRepositoryCRUD;
    private final JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD;

    public JpaConversationGenerationRepository(JpaConversationGenerationRepositoryCRUD jpaConversationGenerationRepositoryCRUD, JpaAuthorRepositoryCRUD jpaAuthorRepositoryCRUD) {
        this.jpaConversationGenerationRepositoryCRUD = jpaConversationGenerationRepositoryCRUD;
        this.jpaAuthorRepositoryCRUD = jpaAuthorRepositoryCRUD;
    }

    @Override
    public ConversationGeneration saveConversationGeneration(ConversationGeneration conversationGeneration) {
        return Optional.of(jpaConversationGenerationRepositoryCRUD.save(ConversationGenerationDomainToJpaMapper
                        .mapForCreate(conversationGeneration, jpaAuthorRepositoryCRUD.getReferenceById(conversationGeneration.getAuthor().getId()))))
                .map(ConversationGenerationJpaToDomainMapper::map)
                .orElseThrow();
    }

    @Override
    public ConversationGeneration getConversationGenerationById(UUID conversationGenerationId) {
        return ConversationGenerationJpaToDomainMapper.map(
                jpaConversationGenerationRepositoryCRUD.getReferenceById(conversationGenerationId)
        );
    }

    @Override
    public void deleteConversationGenerationById(UUID conversationGenerationId) {

    }
}
