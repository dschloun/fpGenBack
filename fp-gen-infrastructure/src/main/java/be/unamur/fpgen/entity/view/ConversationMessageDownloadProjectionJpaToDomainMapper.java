package be.unamur.fpgen.entity.view;

import be.unamur.fpgen.message.download.ConversationMessageDownload;

import java.util.Objects;

public class ConversationMessageDownloadProjectionJpaToDomainMapper {

    public static ConversationMessageDownload map(final ConversationMessageDownloadProjection entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return ConversationMessageDownload.newBuilder()
                .withConversationId(entity.getConversationId())
                .withOrderNumber(entity.getOrderNumber().toString())
                .withType(entity.getType())
                .withContent(entity.getContent())
                .withSenderId(entity.getSenderId())
                .withReceiverId(entity.getReceiverId())
                .build();
    }
}
