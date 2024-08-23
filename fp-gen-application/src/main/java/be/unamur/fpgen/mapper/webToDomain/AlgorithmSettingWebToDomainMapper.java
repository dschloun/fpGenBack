package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.result.AlgorithmSetting;

public class AlgorithmSettingWebToDomainMapper {

    public static AlgorithmSetting map(final be.unamur.model.AlgorithmSetting web){
        return AlgorithmSetting.newBuilder()
                .withParameterName(web.getParameterName())
                .withValue(web.getValue())
                .build();
    }

}