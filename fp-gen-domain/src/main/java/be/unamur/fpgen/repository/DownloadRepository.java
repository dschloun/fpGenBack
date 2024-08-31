package be.unamur.fpgen.repository;

import be.unamur.fpgen.message.download.ConversationMessageDownload;
import be.unamur.fpgen.message.download.InstantMessageDownload;

import java.util.List;

public interface DownloadRepository {

    List<InstantMessageDownload> findAllMessagesByDatasetId(String datasetId);

    List<ConversationMessageDownload> findAllConversationsByDatasetId(String datasetId);
}
