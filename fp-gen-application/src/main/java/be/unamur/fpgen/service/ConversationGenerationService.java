package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.exception.GenerationNotFoundException;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.generation.pagination.ConversationGenerationsPage;
import be.unamur.fpgen.generation.pagination.PagedGenerationsQuery;
import be.unamur.fpgen.mapper.webToDomain.MessageTopicWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.repository.ConversationGenerationRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.GenerationCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class ConversationGenerationService {
    private final ConversationGenerationRepository conversationGenerationRepository;
    private final AuthorService authorService;

    public ConversationGenerationService(ConversationGenerationRepository conversationGenerationRepository, AuthorService authorService) {
        this.conversationGenerationRepository = conversationGenerationRepository;
        this.authorService = authorService;
    }

    @Transactional
    public ConversationGeneration createConversationGeneration(final GenerationCreation command, final UUID authorId) {
        // 0. check if author is registered
        final Author author = authorService.getAuthorById(authorId);
        // 1. save the generation
        return conversationGenerationRepository.saveConversationGeneration(
                ConversationGeneration.newBuilder()
                        .withAuthor(author)
                        .withDetails(getDetail(command, "conversation"))
                        .withQuantity(command.getQuantity())
                        .withTopic(MessageTopicWebToDomainMapper.map(command.getTopic()))
                        .withType(MessageTypeWebToDomainMapper.map(command.getType()))
                        .withSystemPrompt(command.getSystemPrompt())
                        .withUserPrompt(command.getUserPrompt())
                        .build());
    }

    @Transactional
    public ConversationGeneration findConversationGenerationById(final UUID generationId) {
        return conversationGenerationRepository.findConversationGenerationById(generationId)
                .orElseThrow(() -> GenerationNotFoundException.withId(generationId));
    }

    @Transactional
    public ConversationGenerationsPage searchGenerationsPaginate(PagedGenerationsQuery query) {
        //0. get author
        final Author author = query.getGenerationQuery().getAuthorTrigram() != null ? authorService.getAuthorByTrigram(query.getGenerationQuery().getAuthorTrigram()) : null;
        //1. get pageable
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());
        //2. search generations
            return conversationGenerationRepository.findPagination(
                    query.getGenerationQuery().getMessageType(),
                    query.getGenerationQuery().getMessageTopic(),
                    query.getGenerationQuery().getUserPrompt(),
                    query.getGenerationQuery().getSystemPrompt(),
                    query.getGenerationQuery().getQuantity(),
                    query.getGenerationQuery().getAuthorTrigram(),
                    DateUtil.ifNullReturnOldDate(query.getGenerationQuery().getStartDate()),
                    DateUtil.ifNullReturnTomorrow(query.getGenerationQuery().getEndDate()),
                    pageable);
    }

    @Transactional
    public void deleteConversationGenerationById(final UUID generationId) {
        conversationGenerationRepository.deleteConversationGenerationById(generationId);
    }

    private String getDetail(final GenerationCreation command, final String generationType) {
        return String.format("generate %s set with Topic: %s, Type: %s, Quantity: %s,}\n System prompt: %s \n User prompt: %s",
                generationType, command.getTopic(), command.getType(), command.getQuantity(), command.getSystemPrompt(), command.getUserPrompt());
    }
}
