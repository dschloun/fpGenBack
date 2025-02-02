package be.unamur.fpgen.repository;

import be.unamur.fpgen.message.download.ConversationMessageDownload;
import be.unamur.fpgen.message.download.InstantMessageDownload;

import java.util.List;

/**
 * Repository for downloading functionality.
 */
public interface DownloadRepository {

    /**
     * Find all instant messages by dataset id (for instant message dataset)
     * @param datasetId
     * @return a download format
     */
    List<InstantMessageDownload> findAllMessagesByDatasetId(String datasetId);

    /**
     * Find all conversations by dataset id (for conversation dataset)
     * @param datasetId
     * @return a download format
     */
    List<ConversationMessageDownload> findAllConversationsByDatasetId(String datasetId);
}
