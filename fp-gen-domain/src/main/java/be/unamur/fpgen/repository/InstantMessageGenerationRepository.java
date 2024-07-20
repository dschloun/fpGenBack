package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.generation.pagination.GenerationsPage;
import be.unamur.fpgen.instant_message.MessageTopicEnum;
import be.unamur.fpgen.instant_message.MessageTypeEnum;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public interface InstantMessageGenerationRepository {

    InstantMessageGeneration saveInstantMessageGeneration(InstantMessageGeneration instantMessageGeneration);

    Optional<InstantMessageGeneration> findInstantMessageGenerationById(UUID instantMessageGenerationId);

    void deleteInstantMessageGenerationById(UUID instantMessageGenerationId);

    GenerationsPage findPagination(MessageTypeEnum messageType,
                                   MessageTopicEnum messageTopic,
                                   String userPrompt,
                                   String systemPrompt,
                                   Integer quantity,
                                   String authorTrigram,
                                   OffsetDateTime startDate,
                                   OffsetDateTime endDate,
                                   Pageable pageable);
}
