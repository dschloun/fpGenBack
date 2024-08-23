package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.result.AlgorithmSettingEntity;
import be.unamur.fpgen.result.AlgorithmSetting;

public class AlgorithmSettingDomainToJpaMapper {

    public static AlgorithmSettingEntity map(AlgorithmSetting domain) {
        AlgorithmSettingEntity algorithmSettingEntity = new AlgorithmSettingEntity();
        algorithmSettingEntity.setId(domain.getId());
        algorithmSettingEntity.setParameterName(domain.getParameterName());
        algorithmSettingEntity.setValue(domain.getValue());
        return algorithmSettingEntity;
    }
}
