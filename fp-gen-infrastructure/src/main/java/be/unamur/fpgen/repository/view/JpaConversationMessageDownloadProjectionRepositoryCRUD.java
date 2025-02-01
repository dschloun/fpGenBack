package be.unamur.fpgen.repository.view;

import be.unamur.fpgen.entity.instant_message.ConversationInstantMessageEntity;
import be.unamur.fpgen.entity.view.ConversationMessageDownloadProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaConversationMessageDownloadProjectionRepositoryCRUD extends JpaRepository<ConversationInstantMessageEntity, UUID> {

    @Query(nativeQuery = true, value =
            "SELECT id as id, " +
                    "conversation_id as conversationId, " +
                    "order_number as orderNumber, " +
                    "type as type, " +
                    "topic as topic, " +
                    "content as content, " +
                    "sender_id as senderId, " +
                    "receiver_id as receiverId " +
                    "FROM conversation_search_view " +
                    "WHERE dataset_id = :datasetId " +
                    "ORDER BY conversation_id ASC, order_number ASC "
    )
    List<ConversationMessageDownloadProjection> findAllByDatasetId(@Param("datasetId") String datasetId);
}
