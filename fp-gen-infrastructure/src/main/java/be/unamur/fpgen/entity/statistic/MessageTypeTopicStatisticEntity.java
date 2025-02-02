package be.unamur.fpgen.entity.statistic;

import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * This class represents the type/topic distribution
 */
@Entity
@Table(name = "message_type_topic_statistic")
public class MessageTypeTopicStatisticEntity extends BaseUuidEntity {

    private MessageTypeEnum messageType;
    private MessageTopicEnum messageTopic;
    private BigDecimal ratio;
    private StatisticEntity statistic;

    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeEnum messageType) {
        this.messageType = messageType;
    }

    @Column(name = "message_topic", nullable = false)
    @Enumerated(EnumType.STRING)
    public MessageTopicEnum getMessageTopic() {
        return messageTopic;
    }

    public void setMessageTopic(MessageTopicEnum topic) {
        this.messageTopic = topic;
    }

    @Column(name = "ratio", nullable = false)
    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    @ManyToOne
    @JoinColumn(name = "statistic_id", nullable = false)
    public StatisticEntity getStatistic() {
        return statistic;
    }

    public void setStatistic(StatisticEntity statistic) {
        this.statistic = statistic;
    }
}
