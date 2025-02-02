package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.result.AlgorithmSettingEntity;
import be.unamur.fpgen.entity.result.ResultEntity;
import be.unamur.fpgen.result.AlgorithmSetting;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class AlgorithmSettingDomainToJpaMapper {

    public static AlgorithmSettingEntity map(AlgorithmSetting domain, ResultEntity result) {
        AlgorithmSettingEntity algorithmSettingEntity = new AlgorithmSettingEntity();
        algorithmSettingEntity.setParameterName(domain.getParameterName());
        algorithmSettingEntity.setValue(domain.getValue());
        algorithmSettingEntity.setResult(result);
        return algorithmSettingEntity;
    }
}
