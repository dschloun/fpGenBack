package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.repository.ConversationGenerationRepository;
import be.unamur.fpgen.repository.InstantMessageGenerationRepository;
import be.unamur.model.GenerationCreation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
                        .withQuantity(command.getQuantity())
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
                        .withQuantity(command.getQuantity())
                        .build());
    }

    private String getDetail(final GenerationCreation command, final String generationType){
        return String.format("generate %s set with Topic: %s, Type: %s, Quantity: %s,}\n System prompt: %s \n User prompt: %s",
                generationType, command.getTopic(), command.getType(), command.getQuantity(), command.getSystemPrompt(), command.getUserPrompt());
    }
}
