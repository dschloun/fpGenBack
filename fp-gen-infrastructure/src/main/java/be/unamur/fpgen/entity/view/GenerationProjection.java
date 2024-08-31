package be.unamur.fpgen.entity.view;

import java.sql.Timestamp;

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
