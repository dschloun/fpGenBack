package be.unamur.fpgen.repository;

import be.unamur.fpgen.message.download.InstantMessageDownload;

import java.util.List;

public interface DownloadRepository {

    List<InstantMessageDownload> findAllByDatasetId(String datasetId);
}
