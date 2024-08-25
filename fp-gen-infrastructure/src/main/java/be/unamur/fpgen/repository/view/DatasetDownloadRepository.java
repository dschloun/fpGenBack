package be.unamur.fpgen.repository.view;

import be.unamur.fpgen.entity.view.InstantMessageDownloadProjectionJpaToDomainMapper;
import be.unamur.fpgen.message.download.ConversationMessageDownload;
import be.unamur.fpgen.message.download.InstantMessageDownload;
import be.unamur.fpgen.repository.DownloadRepository;
import be.unamur.fpgen.utils.MapperUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatasetDownloadRepository implements DownloadRepository {

    private final JpaInstantMessageDownloadProjectionRepositoryCRUD jpaInstantMessageDownloadProjectionRepositoryCRUD;

    public DatasetDownloadRepository(final JpaInstantMessageDownloadProjectionRepositoryCRUD jpaInstantMessageDownloadProjectionRepositoryCRUD) {
        this.jpaInstantMessageDownloadProjectionRepositoryCRUD = jpaInstantMessageDownloadProjectionRepositoryCRUD;
    }

    @Override
    public List<InstantMessageDownload> findAllMessagesByDatasetId(final String datasetId) {
        return MapperUtil.mapList(
                jpaInstantMessageDownloadProjectionRepositoryCRUD.findAllByDatasetId(datasetId),
                InstantMessageDownloadProjectionJpaToDomainMapper::map);
    }

    @Override
    public List<ConversationMessageDownload> findAllConversationsByDatasetId(String datasetId) {
        return null;
    }
}
