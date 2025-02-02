package be.unamur.fpgen.entity.statistic;

import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Represent the descriptive statistics of a dataset.
 * actually it's more the characteristics of the dataset
 * it shows the distributions between real and fake profile with different metrics
 */
@Entity
@Table(name = "statistic")
public class StatisticEntity extends BaseUuidEntity {
    private Integer total;
    private BigDecimal fakeRatio;
    private BigDecimal realRatio;
    private BigDecimal socialEngineerRatio;
    private BigDecimal harasserRatio;
    private DatasetEntity dataset;
    private Set<MessageTypeTopicStatisticEntity> messageTopicStatisticList = new HashSet<>();

    @Column(name = "total", nullable = false)
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Column(name = "fake_ratio", nullable = false)
    public BigDecimal getFakeRatio() {
        return fakeRatio;
    }

    public void setFakeRatio(BigDecimal fakeRatio) {
        this.fakeRatio = fakeRatio;
    }

    @Column(name = "real_ratio", nullable = false)
    public BigDecimal getRealRatio() {
        return realRatio;
    }

    public void setRealRatio(BigDecimal realRatio) {
        this.realRatio = realRatio;
    }

    @Column(name = "social_engineer_ratio", nullable = false)
    public BigDecimal getSocialEngineerRatio() {
        return socialEngineerRatio;
    }

    public void setSocialEngineerRatio(BigDecimal socialEngineerRatio) {
        this.socialEngineerRatio = socialEngineerRatio;
    }

    @Column(name = "harasser_ratio", nullable = false)
    public BigDecimal getHarasserRatio() {
        return harasserRatio;
    }

    public void setHarasserRatio(BigDecimal harasserRatio) {
        this.harasserRatio = harasserRatio;
    }

    @OneToOne(mappedBy = "statistic")
    public DatasetEntity getDataset() {
        return dataset;
    }

    public void setDataset(DatasetEntity dataset) {
        this.dataset = dataset;
    }

    @OneToMany(mappedBy = "statistic", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<MessageTypeTopicStatisticEntity> getMessageTopicStatisticList() {
        return messageTopicStatisticList;
    }

    public void setMessageTopicStatisticList(Set<MessageTypeTopicStatisticEntity> messageTopicStatisticList) {
        this.messageTopicStatisticList = messageTopicStatisticList;
    }
}
