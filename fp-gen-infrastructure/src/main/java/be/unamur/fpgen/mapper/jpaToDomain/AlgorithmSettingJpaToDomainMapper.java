package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.result.AlgorithmSettingEntity;
import be.unamur.fpgen.result.AlgorithmSetting;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class AlgorithmSettingJpaToDomainMapper {

    public static AlgorithmSetting map(AlgorithmSettingEntity entity) {
        return AlgorithmSetting.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withParameterName(entity.getParameterName())
                .withValue(entity.getValue())
                .build();
    }
}
