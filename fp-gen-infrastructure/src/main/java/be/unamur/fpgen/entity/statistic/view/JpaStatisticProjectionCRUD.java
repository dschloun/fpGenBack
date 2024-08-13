package be.unamur.fpgen.entity.statistic.view;

import be.unamur.fpgen.entity.statistic.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface JpaStatisticProjectionCRUD extends JpaRepository<StatisticEntity, UUID> {

    @Query(nativeQuery = true, value = "SELECT count(*)" +
            " FROM statistic_helper_view" +
            " WHERE dataset_id = :datasetId")
    Integer findTotalByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT count(*)" +
            " FROM statistic_helper_view" +
            " WHERE dataset_id = :datasetId" +
            " AND type = 'GENUINE'")
    Integer findGenuineTotalByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT count(*)" +
            " FROM statistic_helper_view" +
            " WHERE dataset_id = :datasetId" +
            " AND type = 'TROLLING'")
    Integer findTrollingTotalByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT count(*)" +
            " FROM statistic_helper_view" +
            " WHERE dataset_id = :datasetId" +
            " AND type = 'SOCIAL_ENGINEERING'")
    Integer findSocialEngineeringTotalByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT topic, count(*)" +
            " FROM statistic_helper_view" +
            " GROUP BY topic" +
            " HAVING dataset_id = :datasetId")
    Integer findTopicTotalByDatasetId(@Param("datasetId") String datasetId);


}
