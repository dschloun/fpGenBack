package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.exception.GenerationNotFoundException;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.generation.pagination.InstantMessageGenerationsPage;
import be.unamur.fpgen.generation.pagination.PagedGenerationsQuery;
import be.unamur.fpgen.mapper.webToDomain.MessageTopicWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.repository.InstantMessageGenerationRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.GenerationCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class InstantMessageGenerationService {
    private final InstantMessageGenerationRepository instantMessageGenerationRepository;
    private final AuthorService authorService;

    public InstantMessageGenerationService(final InstantMessageGenerationRepository instantMessageGenerationRepository, AuthorService authorService) {
        this.instantMessageGenerationRepository = instantMessageGenerationRepository;
        this.authorService = authorService;
    }

    @Transactional
    public InstantMessageGeneration createInstantMessageGeneration(final GenerationCreation command, final UUID authorId) {
        // 0. check if author is registered
        final Author author = authorService.getAuthorById(authorId);
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
    public InstantMessageGeneration findInstantMessageGenerationById(final UUID generationId) {
        return instantMessageGenerationRepository.findInstantMessageGenerationById(generationId)
                .orElseThrow(() -> GenerationNotFoundException.withId(generationId));
    }

    @Transactional
    public InstantMessageGenerationsPage searchGenerationsPaginate(PagedGenerationsQuery query) {
       //1. get pageable
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());
        //2. search generations
        return instantMessageGenerationRepository.findPagination(
                query.getGenerationQuery().getMessageType(),
                query.getGenerationQuery().getMessageTopic(),
                query.getGenerationQuery().getUserPrompt(),
                query.getGenerationQuery().getSystemPrompt(),
                query.getGenerationQuery().getQuantity(),
                query.getGenerationQuery().getAuthorTrigram(),
                DateUtil.ifNullReturnOldDate(query.getGenerationQuery().getStartDate()),
                DateUtil.ifNullReturnTomorrow(query.getGenerationQuery().getEndDate()),
                query.getGenerationQuery().getNotInDatasetIdList(),
                pageable);
    }

    @Transactional
    public void deleteInstantMessageGenerationById(final UUID generationId) {
        instantMessageGenerationRepository.deleteInstantMessageGenerationById(generationId);
    }

    private String getDetail(final GenerationCreation command, final String generationType) {
        return String.format("generate %s set with Topic: %s, Type: %s, Quantity: %s,}\n System prompt: %s \n User prompt: %s",
                generationType, command.getTopic(), command.getType(), command.getQuantity(), command.getSystemPrompt(), command.getUserPrompt());
    }
}
