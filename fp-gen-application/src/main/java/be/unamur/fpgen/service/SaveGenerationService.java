package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.repository.ConversationGenerationRepository;
import be.unamur.fpgen.repository.InstantMessageGenerationRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.GenerationCreation;
import be.unamur.model.InstantMessageBatchCreation;
import be.unamur.model.InstantMessageCreation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class SaveGenerationService {
    private final InstantMessageGenerationRepository instantMessageGenerationRepository;
    private final ConversationGenerationRepository conversationGenerationRepository;
    private final AuthorService authorService;

    public SaveGenerationService(final InstantMessageGenerationRepository instantMessageGenerationRepository, ConversationGenerationRepository conversationGenerationRepository, AuthorService authorService) {
        this.instantMessageGenerationRepository = instantMessageGenerationRepository;
        this.conversationGenerationRepository = conversationGenerationRepository;
        this.authorService = authorService;
    }

    @Transactional
    public InstantMessageGeneration createInstantMessageGeneration(final GenerationCreation command){
        // 0. check if author is registered
        final Author author = authorService.getAuthorById(command.getAuthorId());
        // 1. save the generation
        return instantMessageGenerationRepository.saveInstantMessageGeneration(
                InstantMessageGeneration.newBuilder()
                        .withAuthor(author)
                        .withDetails(getDetail(command, "instant message"))
                        .withBatch(command.getQuantity() > 1)
                        .build());
    }

    @Transactional
    public ConversationGeneration createConversationGeneration(final GenerationCreation command){
        // 0. check if author is registered
        final Author author = authorService.getAuthorById(command.getAuthorId());
        // 1. save the generation
        return conversationGenerationRepository.saveConversationGeneration(
                ConversationGeneration.newBuilder()
                        .withAuthor(author)
                        .withDetails(getDetail(command, "conversation"))
                        .withBatch(command.getQuantity() > 1)
                        .build());
    }

    private String getDetail(final GenerationCreation command, final String generationType){
        return String.format("generate %s set with Topic: %s, Type: %s, Quantity: %s,}\n System prompt: %s \n User prompt: %s",
                generationType, command.getTopic(), command.getType(), command.getQuantity(), command.getSystemPrompt(), command.getUserPrompt());
    }
}
