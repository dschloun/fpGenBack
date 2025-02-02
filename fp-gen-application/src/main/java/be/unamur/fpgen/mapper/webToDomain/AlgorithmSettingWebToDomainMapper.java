package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.result.AlgorithmSetting;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class AlgorithmSettingWebToDomainMapper {

    public static AlgorithmSetting map(final be.unamur.model.AlgorithmSetting web){
        return AlgorithmSetting.newBuilder()
                .withParameterName(web.getParameterName())
                .withValue(web.getValue())
                .build();
    }

}