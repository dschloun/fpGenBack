package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.exception.GenerationNotFoundException;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.pagination.GenerationPage;
import be.unamur.fpgen.generation.pagination.PagedGenerationsQuery;
import be.unamur.fpgen.mapper.webToDomain.MessageTopicWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.prompt.Prompt;
import be.unamur.fpgen.repository.GenerationRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.GenerationCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

/**
 * Service class for generation
 */
@Service
public class GenerationService implements FindByIdService{
    private final GenerationRepository generationRepository;

    public GenerationService(final GenerationRepository generationRepository) {
        this.generationRepository = generationRepository;
    }

    /**
     * Create a generation
     * @param generationType the type of generation
     * @param command        the generation creation command
     * @param prompt         the prompt
     * @param author         the author
     * @return the created generation
     */
    @Transactional
    public Generation createGeneration(final GenerationTypeEnum generationType, final GenerationCreation command, final Prompt prompt, final Author author) {
       // 1. save the generation
        return generationRepository.saveGeneration(
                Generation.newBuilder()
                        .withGenerationType(generationType)
                        .withAuthor(author)
                        .withDetails(getDetail(command, command.getType().name(), prompt))
                        .withQuantity(command.getQuantity())
                        .withTopic(MessageTopicWebToDomainMapper.map(command.getTopic()))
                        .withType(MessageTypeWebToDomainMapper.map(command.getType()))
                        .withPrompt(prompt)
                        .build());
    }

    /**
     * Find a generation by its id
     * @param generationId the generation id
     * @return the generation
     */
    @Transactional
    public Generation findById(final UUID generationId) {
        return generationRepository.findGenerationById(generationId)
                .orElseThrow(() -> GenerationNotFoundException.withId(generationId));
    }

    /**
     * Search generations paginated
     * @param query the query
     * @return the generation page
     */
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
                query.getGenerationQuery().getPromptVersion(),
                query.getGenerationQuery().getQuantity(),
                query.getGenerationQuery().getAuthorTrigram(),
                DateUtil.ifNullReturnOldDate(query.getGenerationQuery().getStartDate()),
                DateUtil.ifNullReturnTomorrow(query.getGenerationQuery().getEndDate()),
                inDatasetSearch(query) ? query.getGenerationQuery().getInDatasetIdList() : query.getGenerationQuery().getNotInDatasetIdList(),
                inDatasetSearch(query),
                pageable);
    }

    /**
     * Delete a generation by its id
     * @param generationId the generation id
     */
    @Transactional
    public void deleteGenerationById(final UUID generationId) {
        generationRepository.deleteGenerationById(generationId);
    }

    /**
     * Get the detail of a generation
     * @param command the generation creation command
     * @param generationType the generation type
     * @param prompt the prompt
     * @return the detail
     */
    private String getDetail(final GenerationCreation command, final String generationType, final Prompt prompt) {
        return String.format("generate %s set with Topic: %s, Type: %s, Quantity: %s,}\n prompt version: %s",
                generationType, command.getTopic(), command.getType(), command.getQuantity(), prompt.getVersion());
    }

    /**
     * Check if the query is in dataset search
     * @param query the query
     * @return true if the query is in dataset search
     */
    private boolean inDatasetSearch(final PagedGenerationsQuery query) {
        return Objects.nonNull(query.getGenerationQuery().getInDatasetIdList());
    }
}
