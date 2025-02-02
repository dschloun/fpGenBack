package be.unamur.fpgen.repository.view;

import be.unamur.fpgen.entity.view.ConversationMessageDownloadProjectionJpaToDomainMapper;
import be.unamur.fpgen.entity.view.InstantMessageDownloadProjectionJpaToDomainMapper;
import be.unamur.fpgen.message.download.ConversationMessageDownload;
import be.unamur.fpgen.message.download.InstantMessageDownload;
import be.unamur.fpgen.repository.DownloadRepository;
import be.unamur.fpgen.utils.MapperUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * See the specifications in the {@link DownloadRepository} interface.
 */
@Repository
public class DatasetDownloadRepository implements DownloadRepository {

    private final JpaInstantMessageDownloadProjectionRepositoryCRUD jpaInstantMessageDownloadProjectionRepositoryCRUD;
    private final JpaConversationMessageDownloadProjectionRepositoryCRUD jpaConversationMessageDownloadProjectionRepositoryCRUD;

    public DatasetDownloadRepository(final JpaInstantMessageDownloadProjectionRepositoryCRUD jpaInstantMessageDownloadProjectionRepositoryCRUD,
                                     final JpaConversationMessageDownloadProjectionRepositoryCRUD jpaConversationMessageDownloadProjectionRepositoryCRUD) {
        this.jpaInstantMessageDownloadProjectionRepositoryCRUD = jpaInstantMessageDownloadProjectionRepositoryCRUD;
        this.jpaConversationMessageDownloadProjectionRepositoryCRUD = jpaConversationMessageDownloadProjectionRepositoryCRUD;
    }

    @Override
    public List<InstantMessageDownload> findAllMessagesByDatasetId(final String datasetId) {
        return MapperUtil.mapList(
                jpaInstantMessageDownloadProjectionRepositoryCRUD.findAllByDatasetId(datasetId),
                InstantMessageDownloadProjectionJpaToDomainMapper::map);
    }

    @Override
    public List<ConversationMessageDownload> findAllConversationsByDatasetId(final String datasetId) {
        return MapperUtil.mapList(
                jpaConversationMessageDownloadProjectionRepositoryCRUD.findAllByDatasetId(datasetId),
                ConversationMessageDownloadProjectionJpaToDomainMapper::map);
    }
}
