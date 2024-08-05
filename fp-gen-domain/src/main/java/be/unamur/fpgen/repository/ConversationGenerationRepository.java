package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.ConversationGeneration;
import be.unamur.fpgen.generation.pagination.ConversationGenerationsPage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ConversationGenerationRepository {

    ConversationGeneration saveConversationGeneration(ConversationGeneration conversationGeneration);

    Optional<ConversationGeneration> findConversationGenerationById(UUID conversationGenerationId);

    void deleteConversationGenerationById(UUID conversationGenerationId);

    ConversationGenerationsPage findPagination(MessageTypeEnum messageType,
                                               MessageTopicEnum messageTopic,
                                               String userPrompt,
                                               String systemPrompt,
                                               Integer quantity,
                                               String authorTrigram,
                                               OffsetDateTime startDate,
                                               OffsetDateTime endDate,
                                               Pageable pageable);

}
