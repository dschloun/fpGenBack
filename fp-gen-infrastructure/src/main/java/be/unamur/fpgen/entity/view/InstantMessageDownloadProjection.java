package be.unamur.fpgen.entity.view;

/**
 * This interface is used to define the projection of the instant message entity that will be used to download the instant messages.
 */
public interface InstantMessageDownloadProjection {
    String getId();
    String getType();
    String getTopic();
    String getContent();
}
