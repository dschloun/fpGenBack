package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.exception.GenerationNotFoundException;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.generation.pagination.GenerationsPage;
import be.unamur.fpgen.generation.pagination.PagedGenerationsQuery;
import be.unamur.fpgen.mapper.webToDomain.MessageTopicWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.repository.ConversationGenerationRepository;
import be.unamur.fpgen.repository.GenerationRepository;
import be.unamur.fpgen.repository.InstantMessageGenerationRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.GenerationCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class GenerationService {
    private final InstantMessageGenerationRepository instantMessageGenerationRepository;
    private final ConversationGenerationRepository conversationGenerationRepository;
    private final AuthorService authorService;

    public GenerationService(final InstantMessageGenerationRepository instantMessageGenerationRepository, ConversationGenerationRepository conversationGenerationRepository, AuthorService authorService) {
        this.instantMessageGenerationRepository = instantMessageGenerationRepository;
        this.conversationGenerationRepository = conversationGenerationRepository;
        this.authorService = authorService;
    }

    @Transactional
    public InstantMessageGeneration createInstantMessageGeneration(final GenerationCreation command) {
        // 0. check if author is registered
        final Author author = authorService.getAuthorById(command.getAuthorId());
        // 1. save the generation
        return instantMessageGenerationRepository.saveInstantMessageGeneration(
                InstantMessageGeneration.newBuilder()
                        .withAuthor(author)
                        .withDetails(getDetail(command, "instant message"))
                        .withQuantity(command.getQuantity())
                        .withTopic(MessageTopicWebToDomainMapper.map(command.getTopic()))
                        .withType(MessageTypeWebToDomainMapper.map(command.getType()))
                        .withSystemPrompt(command.getSystemPrompt())
                        .withUserPrompt(command.getUserPrompt())
                        .build());
    }

    @Transactional
    public ConversationGeneration createConversationGeneration(final GenerationCreation command) {
        // 0. check if author is registered
        final Author author = authorService.getAuthorById(command.getAuthorId());
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
    public InstantMessageGeneration findInstantMessageGenerationById(final UUID generationId) {
        return instantMessageGenerationRepository.findInstantMessageGenerationById(generationId)
                .orElseThrow(() -> GenerationNotFoundException.withId(generationId));
    }

    @Transactional
    public ConversationGeneration findConversationGenerationById(final UUID generationId) {
        return conversationGenerationRepository.findConversationGenerationById(generationId)
                .orElseThrow(() -> GenerationNotFoundException.withId(generationId));
    }

    private String getDetail(final GenerationCreation command, final String generationType) {
        return String.format("generate %s set with Topic: %s, Type: %s, Quantity: %s,}\n System prompt: %s \n User prompt: %s",
                generationType, command.getTopic(), command.getType(), command.getQuantity(), command.getSystemPrompt(), command.getUserPrompt());
    }

    @Transactional
    public GenerationsPage searchGenerationsPaginate(PagedGenerationsQuery query) {
        //0. get author
        final Author author = query.getGenerationQuery().getAuthorTrigram() != null ? authorService.getAuthorByTrigram(query.getGenerationQuery().getAuthorTrigram()) : null;
        //1. get pageable
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());
        //2. search generations
        if (GenerationTypeEnum.CONVERSATION.equals(query.getGenerationQuery().getGenerationType())) {
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
        } else if (GenerationTypeEnum.INSTANT_MESSAGE.equals(query.getGenerationQuery().getGenerationType())) {
            return instantMessageGenerationRepository.findPagination(
                    query.getGenerationQuery().getMessageType(),
                    query.getGenerationQuery().getMessageTopic(),
                    query.getGenerationQuery().getUserPrompt(),
                    query.getGenerationQuery().getSystemPrompt(),
                    query.getGenerationQuery().getQuantity(),
                    query.getGenerationQuery().getAuthorTrigram(),
                    DateUtil.ifNullReturnOldDate(query.getGenerationQuery().getStartDate()),
                    DateUtil.ifNullReturnTomorrow(query.getGenerationQuery().getEndDate()),
                    pageable);
        } else {
            throw new IllegalArgumentException("Generation type not supported");
        }
    }
}
