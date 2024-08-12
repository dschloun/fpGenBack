package be.unamur.fpgen.entity.statistic;

import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.message.MessageTopicEnum;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "message_topic_statistic")
public class MessageTopicStatisticEntity extends BaseUuidEntity {

    private MessageTopicEnum messageTopic;
    private BigDecimal ratio;
    private StatisticEntity statistic;

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
