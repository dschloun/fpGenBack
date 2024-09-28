package be.unamur.fpgen.repository.statistic;

import be.unamur.fpgen.entity.statistic.StatisticEntity;
import be.unamur.fpgen.statistic.TypeTopicDistributionProjection;
import be.unamur.fpgen.message.MessageTopicEnum;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaStatisticRepositoryCRUD extends JpaRepository<StatisticEntity, UUID> {

    Optional<StatisticEntity> findByDatasetId(UUID datasetId);

    @Query(nativeQuery = true, value = "SELECT sum(message_quantity)" +
            " FROM statistic_helper_view" +
            " WHERE dataset_id = :datasetId")
    Optional<Integer> findTotalByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT sum(message_quantity)" +
            " FROM statistic_helper_view" +
            " WHERE dataset_id = :datasetId" +
            " AND message_type = 'GENUINE'")
    Optional<Integer> findGenuineTotalByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT sum(message_quantity)" +
            " FROM statistic_helper_view" +
            " WHERE dataset_id = :datasetId" +
            " AND message_type = 'HARASSMENT'")
    Optional<Integer> findHarassmentTotalByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT sum(message_quantity)" +
            " FROM statistic_helper_view" +
            " WHERE dataset_id = :datasetId" +
            " AND message_type = 'SOCIAL_ENGINEERING'")
    Optional<Integer> findSocialEngineeringTotalByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT topic, sum(message_quantity)" +
            " FROM statistic_helper_view" +
            " GROUP BY dataset_id, message_topic" +
            " HAVING dataset_id = :datasetId")
    List<Pair<MessageTopicEnum, Integer>> findTopicDistributionByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT message_type as type, message_topic as topic, sum(message_quantity) as quantity" +
            " FROM statistic_helper_view" +
            " GROUP BY dataset_id, message_type, message_topic" +
            " HAVING dataset_id = :datasetId")
    List<TypeTopicDistributionProjection> findTypeTopicDistributionByDatasetId(@Param("datasetId") String datasetId);
}
