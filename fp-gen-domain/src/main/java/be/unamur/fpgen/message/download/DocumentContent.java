package be.unamur.fpgen.message.download;

import org.springframework.core.io.Resource;

/**
 * Represents the content of a downloaded document.
 */
public class DocumentContent {
    private final String fileName;
    private final String mimeType;
    private final Long length;
    private final Resource contentStream;

    private DocumentContent(Builder builder) {
        fileName = builder.fileName;
        mimeType = builder.mimeType;
        length = builder.length;
        contentStream = builder.contentStream;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Long getLength() {
        return length;
    }

    public Resource getContentStream() {
        return contentStream;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String fileName;
        private String mimeType;
        private Long length;
        private Resource contentStream;

        private Builder() {
        }

        public Builder withFileName(String val) {
            fileName = val;
            return this;
        }

        public Builder withMimeType(String val) {
            mimeType = val;
            return this;
        }

        public Builder withLength(Long val) {
            length = val;
            return this;
        }

        public Builder withContentStream(Resource val) {
            contentStream = val;
            return this;
        }

        public DocumentContent build() {
            return new DocumentContent(this);
        }
    }
}
