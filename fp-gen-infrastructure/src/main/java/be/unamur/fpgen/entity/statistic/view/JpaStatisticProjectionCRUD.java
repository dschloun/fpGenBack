package be.unamur.fpgen.entity.statistic.view;

import be.unamur.fpgen.entity.statistic.StatisticEntity;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import liquibase.repackaged.org.apache.commons.lang3.tuple.Pair;
import liquibase.repackaged.org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaStatisticProjectionCRUD extends JpaRepository<StatisticEntity, UUID> {

    @Query(nativeQuery = true, value = "SELECT sum(message_quantity)" +
            " FROM statistic_helper_view" +
            " WHERE dataset_id = :datasetId")
    Integer findTotalByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT sum(message_quantity)" +
            " FROM statistic_helper_view" +
            " WHERE dataset_id = :datasetId" +
            " AND message_type = 'GENUINE'")
    Integer findGenuineTotalByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT sum(message_quantity)" +
            " FROM statistic_helper_view" +
            " WHERE dataset_id = :datasetId" +
            " AND message_type = 'TROLLING'")
    Integer findTrollingTotalByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT sum(message_quantity)" +
            " FROM statistic_helper_view" +
            " WHERE dataset_id = :datasetId" +
            " AND message_type = 'SOCIAL_ENGINEERING'")
    Integer findSocialEngineeringTotalByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT topic, sum(message_quantity)" +
            " FROM statistic_helper_view" +
            " GROUP BY dataset_id, message_topic" +
            " HAVING dataset_id = :datasetId")
    List<Pair<MessageTopicEnum, Integer>> findTopicTotalByDatasetId(@Param("datasetId") String datasetId);

    @Query(nativeQuery = true, value = "SELECT type, topic, sum(message_quantity)" +
            " FROM statistic_helper_view" +
            " GROUP BY dataset_id, message_type, message_topic" +
            " HAVING dataset_id = :datasetId")
    List<Triple<MessageTypeEnum, MessageTopicEnum, Integer>>  findTypeTopicTotalByDatasetId(@Param("datasetId") String datasetId);



}
