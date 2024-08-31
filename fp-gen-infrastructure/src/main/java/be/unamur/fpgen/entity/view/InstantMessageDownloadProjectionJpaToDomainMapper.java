package be.unamur.fpgen.entity.view;

import be.unamur.fpgen.message.download.InstantMessageDownload;

import java.util.Objects;

public class InstantMessageDownloadProjectionJpaToDomainMapper {

    public static InstantMessageDownload map(final InstantMessageDownloadProjection entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return InstantMessageDownload.newBuilder()
                .withType(entity.getType())
                .withContent(entity.getContent())
                .build();
    }
}
