package be.unamur.fpgen.entity.view;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public interface GenerationProjection {
    String getId();
    OffsetDateTime getCreationDate();
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
