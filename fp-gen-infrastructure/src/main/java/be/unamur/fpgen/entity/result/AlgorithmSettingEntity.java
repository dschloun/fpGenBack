package be.unamur.fpgen.entity.result;


import be.unamur.fpgen.entity.base.BaseUuidEntity;

import javax.persistence.*;

/**
 * This class represents a setting for an algorithm.
 * It is composed of a parameter name and a value.
 * It's used in the result to indicate what were the settings used by the model
 */
@Entity
@Table(name = "algorithm_setting")
public class AlgorithmSettingEntity extends BaseUuidEntity {

    private ResultEntity result;
    private String parameterName;
    private String value;

    @ManyToOne
    @JoinColumn(name = "result_id")
    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    @Column(name = "parameter_name", nullable = false)
    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    @Column(name = "value", nullable = false)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
