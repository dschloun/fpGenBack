package be.unamur.fpgen.entity.result;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.base.BaseUuidEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "result")
public class ResultEntity extends BaseUuidEntity {
    private DatasetEntity dataset;
    private AuthorEntity authorId;
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
    public AuthorEntity getAuthorId() {
        return authorId;
    }

    public void setAuthorId(AuthorEntity authorId) {
        this.authorId = authorId;
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

//  - changeSet:
//id: add-result-table
//author: dsc
//changes:
//        - createTable:
//tableName: result
//columns:
//        - column:
//name: id
//type: uuid
//constraints:
//        - primaryKey: true
//        - primaryKeyName: pk_result
//                    - nullable: false
//        - unique: true
//        - column:
//name: creation_date
//type: datetime
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: modification_date
//type: datetime
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: dataset_id
//type: uuid
//constraints:
//        - nullable: false
//        - unique: false
//        - foreignKeyName: fk_result_dataset
//                    - references: dataset(id)
//                    - onDelete: CASCADE
//              - column:
//name: author_id
//type: uuid
//constraints:
//        - nullable: false
//        - unique: false
//        - foreignKeyName: fk_result_author
//                    - references: author(id)
//              - column:
//name: experiment_date
//type: datetime
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: machine_details
//type: varchar(255)
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: algorithm_type
//type: varchar(255)
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: other_setting_details
//type: varchar(255)
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: accuracy
//type: numeric
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: precision
//type: numeric
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: recall
//type: numeric
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: f1_score
//type: numeric
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: pr_auc
//type: numeric
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: fp_rate
//type: numeric
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: fn_rate
//type: numeric
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: tp_rate
//type: numeric
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: tn_rate
//type: numeric
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: appreciation
//type: varchar(255)
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: comment
//type: text
//constraints:
//        - nullable: false
//        - unique: false
//
//        - createTable:
//tableName: algorithm_setting
//columns:
//        - column:
//name: id
//type: uuid
//constraints:
//        - primaryKey: true
//        - primaryKeyName: pk_algorithm_setting
//                    - nullable: false
//        - unique: true
//        - column:
//name: creation_date
//type: datetime
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: modification_date
//type: datetime
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: parameter_name
//type: varchar(255)
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: value
//type: varchar(255)
//constraints:
//        - nullable: false
//        - unique: false
//        - column:
//name: result_id
//type: uuid
//constraints:
//        - nullable: false
//        - unique: false
//        - foreignKeyName: fk_algorithm_setting_result
//                    - references: result(id)
//                    - onDelete: CASCADE
