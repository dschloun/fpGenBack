package be.unamur.fpgen.entity.view;

import java.sql.Timestamp;

/**
 * This interface is used to define the projection of the generation entity that will be used to download the generations.
 */
public interface GenerationProjection {
    String getId();
    Timestamp getCreationDate();
    String getKind();
    String getGenerationId();
    String getAuthorTrigram();
    String getDetails();
    Integer getPromptVersion();
    String getTopic();
    String getType();
    Integer getQuantity();
    String getDatasetId();
}
