package be.unamur.fpgen.entity.result;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represent a result obtain by a ML algorithm on a given dataset.
 */
@Entity
@Table(name = "result")
public class ResultEntity extends BaseUuidEntity {
    private DatasetEntity dataset;
    private AuthorEntity author;
    private OffsetDateTime experimentDate;
    private String machineDetails;
    private String algorithmType;
    private Set<AlgorithmSettingEntity> algorithmSettingList = new HashSet<>();
    private String otherSettingDetails;
    private BigDecimal accuracy;
    private BigDecimal precision;
    private BigDecimal recall;
    private BigDecimal f1Score;
    private BigDecimal prAuc;
    private BigDecimal fpRate;
    private BigDecimal fnRate;
    private BigDecimal tpRate;
    private BigDecimal tnRate;
    private String appreciation;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "dataset_id", nullable = false)
    public DatasetEntity getDataset() {
        return dataset;
    }

    public void setDataset(DatasetEntity dataset) {
        this.dataset = dataset;
    }

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity authorId) {
        this.author = authorId;
    }

    @Column(name = "experiment_date")
    public OffsetDateTime getExperimentDate() {
        return experimentDate;
    }

    public void setExperimentDate(OffsetDateTime experimentDate) {
        this.experimentDate = experimentDate;
    }

    @Column(name = "machine_details")
    public String getMachineDetails() {
        return machineDetails;
    }

    public void setMachineDetails(String machineDetails) {
        this.machineDetails = machineDetails;
    }

    @Column(name = "algorithm_type", nullable = false)
    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<AlgorithmSettingEntity> getAlgorithmSettingList() {
        return algorithmSettingList;
    }

    public void setAlgorithmSettingList(Set<AlgorithmSettingEntity> algorithmSettingList) {
        this.algorithmSettingList = algorithmSettingList;
    }

    public ResultEntity addAlgorithmSetting(Set<AlgorithmSettingEntity> algorithmSettingList) {
        this.algorithmSettingList.clear();
        for(AlgorithmSettingEntity s : algorithmSettingList) {
            getAlgorithmSettingList().add(s);
            s.setResult(this);
        }
        return this;
    }

    @Column(name = "other_setting_details")
    public String getOtherSettingDetails() {
        return otherSettingDetails;
    }

    public void setOtherSettingDetails(String otherSettingDetails) {
        this.otherSettingDetails = otherSettingDetails;
    }

    @Column(name = "accuracy")
    public BigDecimal getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(BigDecimal accuracy) {
        this.accuracy = accuracy;
    }

    @Column(name = "precision")
    public BigDecimal getPrecision() {
        return precision;
    }

    public void setPrecision(BigDecimal precision) {
        this.precision = precision;
    }

    @Column(name = "recall")
    public BigDecimal getRecall() {
        return recall;
    }

    public void setRecall(BigDecimal recall) {
        this.recall = recall;
    }

    @Column(name = "f1_score")
    public BigDecimal getF1Score() {
        return f1Score;
    }

    public void setF1Score(BigDecimal f1Score) {
        this.f1Score = f1Score;
    }

    @Column(name = "pr_auc")
    public BigDecimal getPrAuc() {
        return prAuc;
    }

    public void setPrAuc(BigDecimal prAuc) {
        this.prAuc = prAuc;
    }

    @Column(name = "fp_rate")
    public BigDecimal getFpRate() {
        return fpRate;
    }

    public void setFpRate(BigDecimal fpRate) {
        this.fpRate = fpRate;
    }

    @Column(name = "fn_rate")
    public BigDecimal getFnRate() {
        return fnRate;
    }

    public void setFnRate(BigDecimal fnRate) {
        this.fnRate = fnRate;
    }

    @Column(name = "tp_rate")
    public BigDecimal getTpRate() {
        return tpRate;
    }

    public void setTpRate(BigDecimal tpRate) {
        this.tpRate = tpRate;
    }

    @Column(name = "tn_rate")
    public BigDecimal getTnRate() {
        return tnRate;
    }

    public void setTnRate(BigDecimal tnRate) {
        this.tnRate = tnRate;
    }

    @Column(name = "appreciation")
    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}