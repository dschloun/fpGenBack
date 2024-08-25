package be.unamur.fpgen.repository.view;

import be.unamur.fpgen.entity.instant_message.InstantMessageEntity;
import be.unamur.fpgen.entity.view.InstantMessageDownloadProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaInstantMessageDownloadProjectionRepositoryCRUD extends JpaRepository<InstantMessageEntity, UUID> {

    @Query(nativeQuery = true, value =
            "SELECT id as id, " +
                    "type as type, " +
                    "topic as topic, " +
                    "content as content " +
                    "FROM message_search_view " +
                    "WHERE dataset_id = :datasetId "

    )
    List<InstantMessageDownloadProjection> findAllByDatasetId(@Param("datasetId") String datasetId);
}
