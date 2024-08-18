package be.unamur.fpgen.entity.view;

import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public interface GenerationProjection {
    String getId();
    Timestamp getCreationDate();
    String getKind();

    String getGenerationId();
    String getAuthorTrigram();
    String getDetails();
    String getUserPrompt();
    String getTopic();
    String getType();
    Integer getQuantity();
    String getDatasetId();
}
