package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.context.UserContextHolder;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.exception.GenerationNotFoundException;
import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.pagination.GenerationPage;
import be.unamur.fpgen.generation.pagination.PagedGenerationsQuery;
import be.unamur.fpgen.mapper.webToDomain.MessageTopicWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.prompt.Prompt;
import be.unamur.fpgen.repository.GenerationRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.GenerationCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class GenerationService implements FindByIdService{
    private final GenerationRepository generationRepository;
    private final AuthorService authorService;
    private final PromptService promptService;

    public GenerationService(final GenerationRepository generationRepository, AuthorService authorService, PromptService promptService) {
        this.generationRepository = generationRepository;
        this.authorService = authorService;
        this.promptService = promptService;
    }

    @Transactional
    public Generation createGeneration(final GenerationTypeEnum generationType, final GenerationCreation command, final Prompt prompt, final Author author) {
        // 0. check if author is registered
//        final Author author = authorService.getAuthorByTrigram(UserContextHolder.getContext().getTrigram());
        // 0.1 get prompt version
//        final Prompt prompt = Optional.ofNullable(command.getPromptVersion()).map(v -> promptService.findByDatasetTypeAndMessageTypeAndVersion(DatasetTypeEnum.valueOf(generationType.name()), MessageTypeEnum.valueOf(command.getType().name()), v)
//                        .orElse(promptService.getDefaultPrompt(DatasetTypeEnum.INSTANT_MESSAGE, MessageTypeEnum.valueOf(command.getType().name()))))
//                .orElse(promptService.getDefaultPrompt(DatasetTypeEnum.valueOf(generationType.name()), MessageTypeEnum.valueOf(command.getType().name()))); //todo check what append if version do not exist...
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

    @Transactional
    public Generation findById(final UUID generationId) {
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
                query.getGenerationQuery().getPromptVersion(),
                query.getGenerationQuery().getQuantity(),
                query.getGenerationQuery().getAuthorTrigram(),
                DateUtil.ifNullReturnOldDate(query.getGenerationQuery().getStartDate()),
                DateUtil.ifNullReturnTomorrow(query.getGenerationQuery().getEndDate()),
                inDatasetSearch(query) ? query.getGenerationQuery().getInDatasetIdList() : query.getGenerationQuery().getNotInDatasetIdList(),
                inDatasetSearch(query),
                pageable);
    }

    @Transactional
    public void deleteGenerationById(final UUID generationId) {
        generationRepository.deleteGenerationById(generationId);
    }

    private String getDetail(final GenerationCreation command, final String generationType, final Prompt prompt) {
        return String.format("generate %s set with Topic: %s, Type: %s, Quantity: %s,}\n prompt version: %s",
                generationType, command.getTopic(), command.getType(), command.getQuantity(), prompt.getVersion());
    }

    private boolean inDatasetSearch(final PagedGenerationsQuery query) {
        return Objects.nonNull(query.getGenerationQuery().getInDatasetIdList());
    }
}
