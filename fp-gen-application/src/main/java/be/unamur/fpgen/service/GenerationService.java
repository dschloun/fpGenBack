package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.exception.GenerationNotFoundException;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.pagination.GenerationPage;
import be.unamur.fpgen.generation.pagination.PagedGenerationsQuery;
import be.unamur.fpgen.mapper.webToDomain.MessageTopicWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.repository.GenerationRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.GenerationCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

@Service
public class GenerationService {
    private final GenerationRepository generationRepository;
    private final AuthorService authorService;

    public GenerationService(final GenerationRepository generationRepository, AuthorService authorService) {
        this.generationRepository = generationRepository;
        this.authorService = authorService;
    }

    @Transactional
    public Generation createGeneration(final GenerationTypeEnum type, final GenerationCreation command, final UUID authorId) {
        // 0. check if author is registered
        final Author author = authorService.getAuthorById(authorId);
        // 1. save the generation
        return generationRepository.saveGeneration(
                Generation.newBuilder()
                        .withGenerationType(type)
                        .withAuthor(author)
                        .withDetails(getDetail(command, type.toString()))
                        .withQuantity(command.getQuantity())
                        .withTopic(MessageTopicWebToDomainMapper.map(command.getTopic()))
                        .withType(MessageTypeWebToDomainMapper.map(command.getType()))
                        .withSystemPrompt(command.getSystemPrompt())
                        .withUserPrompt(command.getUserPrompt())
                        .build());
    }

    @Transactional
    public Generation findGenerationById(final UUID generationId) {
        return generationRepository.findGenerationById(generationId)
                .orElseThrow(() -> GenerationNotFoundException.withId(generationId));
    }

    @Transactional
    public GenerationPage searchGenerationsPaginate(PagedGenerationsQuery query) {
       //1. get pageable
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());
        //2. search generations
        return generationRepository.findPagination(
                query.getGenerationQuery().getGenerationType(),
                query.getGenerationQuery().getMessageType(),
                query.getGenerationQuery().getMessageTopic(),
                query.getGenerationQuery().getUserPrompt(),
                query.getGenerationQuery().getSystemPrompt(),
                query.getGenerationQuery().getQuantity(),
                query.getGenerationQuery().getAuthorTrigram(),
                DateUtil.ifNullReturnOldDate(query.getGenerationQuery().getStartDate()),
                DateUtil.ifNullReturnTomorrow(query.getGenerationQuery().getEndDate()),
                query.getGenerationQuery().getNotInDatasetIdList(),
                query.getGenerationQuery().getInDatasetIdList(),
                Objects.nonNull(query.getGenerationQuery().getInDatasetIdList()),
                pageable);
    }

    @Transactional
    public void deleteGenerationById(final UUID generationId) {
        generationRepository.deleteGenerationById(generationId);
    }

    private String getDetail(final GenerationCreation command, final String generationType) {
        return String.format("generate %s set with Topic: %s, Type: %s, Quantity: %s,}\n System prompt: %s \n User prompt: %s",
                generationType, command.getTopic(), command.getType(), command.getQuantity(), command.getSystemPrompt(), command.getUserPrompt());
    }
}
