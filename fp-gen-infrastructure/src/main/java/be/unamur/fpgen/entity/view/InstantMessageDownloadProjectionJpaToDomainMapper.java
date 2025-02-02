package be.unamur.fpgen.entity.view;

import be.unamur.fpgen.message.download.InstantMessageDownload;

import java.util.Objects;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class InstantMessageDownloadProjectionJpaToDomainMapper {

    public static InstantMessageDownload map(final InstantMessageDownloadProjection entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return InstantMessageDownload.newBuilder()
                .withMessageId(entity.getId())
                .withType(entity.getType())
                .withContent(entity.getContent())
                .withTopic(entity.getTopic())
                .build();
    }
}
