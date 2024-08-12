package be.unamur.fpgen.entity.statistic;

import be.unamur.fpgen.entity.base.BaseUuidEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "message_topic_statistic")
public class MessageTopicStatisticEntity extends BaseUuidEntity {

    private String topicName;
    private BigDecimal ratio;
    private StatisticEntity statistic;

    @Column(name = "topic_name", nullable = false)
    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
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
