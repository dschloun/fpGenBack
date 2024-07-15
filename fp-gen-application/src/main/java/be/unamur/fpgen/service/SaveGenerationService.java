package be.unamur.fpgen.service;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.repository.ConversationGenerationRepository;
import be.unamur.fpgen.repository.InstantMessageGenerationRepository;
import be.unamur.fpgen.utils.DateUtil;
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
    public InstantMessageGeneration createInstantMessageGeneration(final InstantMessageBatchCreation command){
        // 0. check if author is registered
        final Author author = authorService.getAuthorById(UUID.randomUUID()); //fixme put the author id in the command
        // 1. save the generation
        return instantMessageGenerationRepository.saveInstantMessageGeneration(
                InstantMessageGeneration.newBuilder()
                        .withAuthor(author)
                        .withDetails(produceDetails(command))
                        .withBatch(command.getInstantMessageCreationList().get(0).getQuantity() > 1)
                        .build());
    }

//    @Transactional
//    public ConversationGeneration createConversationGeneration(final InstantMessageBatchCreation command){
//        // 0. check if author is registered
//        final Author author = authorService.getAuthorById(UUID.randomUUID()); //fixme put the author id in the command
//        // 1. save the generation
//        return instantMessageGenerationRepository.saveInstantMessageGeneration(
//                InstantMessageGeneration.newBuilder()
//                        .withAuthor(author)
//                        .withDetails(produceDetails(command))
//                        .withBatch(command.getInstantMessageCreationList().get(0).getQuantity() > 1)
//                        .build());
//    }

    private String produceDetails(final InstantMessageBatchCreation command){
        final String begin = "generate instant message set [\n";
        final String end = String.format("\n], author: %s \n date: %s", "DSC", DateUtil.convertOffsetDateTimeToString(OffsetDateTime.now()));
        final StringBuilder loopText = new StringBuilder();
        command.getInstantMessageCreationList()
                .forEach(t -> {
                    loopText.append(getSingleBatchDetail(t));
                });
        return begin + loopText + end;
    }

    private String getSingleBatchDetail(final InstantMessageCreation command){
        return String.format("{Topic: %s, Type: %s, Quantity: %s}\s", command.getMessageTopic(), command.getMessageType(), command.getQuantity());
    }
}
