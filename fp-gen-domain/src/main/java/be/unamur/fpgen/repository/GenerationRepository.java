package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.AbstractGeneration;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.pagination.AbstractGenerationPage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenerationRepository {

    AbstractGeneration saveGeneration(AbstractGeneration generation);

    Optional<AbstractGeneration> findGenerationById(UUID generationId);

    void deleteGenerationById(UUID generationId);

    AbstractGenerationPage findPagination(GenerationTypeEnum type,
                                          MessageTypeEnum messageType,
                                          MessageTopicEnum messageTopic,
                                          String userPrompt,
                                          String systemPrompt,
                                          Integer quantity,
                                          String authorTrigram,
                                          OffsetDateTime startDate,
                                          OffsetDateTime endDate,
                                          List<UUID> notInDatasetIdList,
                                          List<UUID> inDatasetIdList,
                                          boolean isIn,
                                          Pageable pageable);
}
