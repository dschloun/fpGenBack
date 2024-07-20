package be.unamur.fpgen.repository;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.generation.AbstractGeneration;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.generation.pagination.GenerationsPage;
import be.unamur.fpgen.instant_message.MessageTopicEnum;
import be.unamur.fpgen.instant_message.MessageTypeEnum;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;

public interface GenerationRepository {

    GenerationsPage findPagination(MessageTypeEnum messageType,
                                   MessageTopicEnum messageTopic,
                                   String userPrompt,
                                   String systemPrompt,
                                   Integer quantity,
                                   Author author,
                                   OffsetDateTime startDate,
                                   OffsetDateTime endDate,
                                   Pageable pageable);


}
