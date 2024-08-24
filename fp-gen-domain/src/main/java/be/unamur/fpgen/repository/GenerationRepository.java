package be.unamur.fpgen.repository;

import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.pagination.GenerationPage;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenerationRepository {

    Generation saveGeneration(Generation generation);

    Optional<Generation> findGenerationById(UUID generationId);

    void deleteGenerationById(UUID generationId);

    GenerationPage findPagination(GenerationTypeEnum type,
                                  MessageTypeEnum messageType,
                                  MessageTopicEnum messageTopic,
                                  String userPrompt,
                                  String systemPrompt,
                                  Integer quantity,
                                  String authorTrigram,
                                  OffsetDateTime startDate,
                                  OffsetDateTime endDate,
                                  List<UUID> datasetIdList,
                                  boolean isIn,
                                  Pageable pageable);
}
