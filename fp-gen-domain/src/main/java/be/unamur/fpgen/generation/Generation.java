package be.unamur.fpgen.generation;

import be.unamur.fpgen.AbstractItem;
import be.unamur.fpgen.BaseUuidDomain;
import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.utils.DateUtil;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @overview: generation is a class that represents the generation of a message, message batch, conversation or conversation batch
 * Generation is immutable.
 * @specfield generationId: String // technical identifier of the generation
 * @specfield generationDate: OffsetDateTime // date of the generation
 * @specfield authorTrigram: String // author trigram of the generation
 * format 'trigram' ex: JDO (John Doe)
 * @specfield details: String // details of the generation
 */
public class Generation extends AbstractItem {
    // members
    private final String generationId;
    private final GenerationTypeEnum generationType;
    private final Author author;
    private final String details;
    private final Integer quantity;
    private final MessageTypeEnum type;
    private final MessageTopicEnum topic;
    private final String systemPrompt;
    private final String userPrompt;
    private final Set<AbstractItem> itemList = new HashSet<>();

    private Generation(Builder builder) {
        super(builder.getId(), builder.getCreationDate(), builder.getModificationDate());
        generationId = builder.generationId;
        generationType = builder.generationType;
        author = builder.author;
        details = builder.details;
        quantity = builder.quantity;
        type = builder.type;
        topic = builder.topic;
        systemPrompt = builder.systemPrompt;
        userPrompt = builder.userPrompt;
        itemList.addAll(builder.itemList);
    }

    public GenerationTypeEnum getGenerationType() {
        return generationType;
    }

    public String getGenerationId() {
        return generationId;
    }

    public Author getAuthor() {
        return author;
    }

    public String getDetails() {
        return details;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public MessageTypeEnum getType() {
        return type;
    }

    public MessageTopicEnum getTopic() {
        return topic;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public Set<AbstractItem> getItemList() {
        return itemList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder extends AbstractItemBuilder<Builder> {
        private String generationId;
        private GenerationTypeEnum generationType;
        private Author author;
        private String details;
        private Integer quantity;
        private MessageTypeEnum type;
        private MessageTopicEnum topic;
        private String systemPrompt;
        private String userPrompt;
        private Set<AbstractItem> itemList = new HashSet<>();

        private Builder() {
        }

        public Builder withGenerationId(String val) {
            generationId = val;
            return this;
        }

        public Builder withGenerationType(GenerationTypeEnum val) {
            generationType = val;
            return this;
        }

        public Builder withAuthor(Author val) {
            author = val;
            return this;
        }

        public Builder withDetails(String val) {
            details = val;
            return this;
        }

        public Builder withQuantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder withType(MessageTypeEnum val) {
            type = val;
            return this;
        }

        public Builder withTopic(MessageTopicEnum val) {
            topic = val;
            return this;
        }

        public Builder withSystemPrompt(String val) {
            systemPrompt = val;
            return this;
        }

        public Builder withUserPrompt(String val) {
            userPrompt = val;
            return this;
        }

        public Builder withItemList(Set<AbstractItem> val) {
            itemList = val;
            return this;
        }

        public Generation build() {
            generationId = generateGenerationId(GenerationTypeEnum.INSTANT_MESSAGE.equals(generationType) ? "IM" : "C");
            return new Generation(this);
        }

        public String returnBatchOrSingle(){
            return this.quantity > 1 ? "BATCH" : "SINGLE";
        }

        public String generateGenerationId(final String generationType) {
            return String.format("%s-%s-%s-%s", generationType, this.returnBatchOrSingle(), this.author.getTrigram(), DateUtil.convertOffsetDateTimeMillisToString(OffsetDateTime.now()));
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
