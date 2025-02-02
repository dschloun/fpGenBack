package be.unamur.fpgen.mapper.domainToWeb;


import be.unamur.model.AlgorithmSetting;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class AlgorithmSettingDomainToWebMapper {

    public static AlgorithmSetting map(final be.unamur.fpgen.result.AlgorithmSetting domain){
        return new AlgorithmSetting()
                .parameterName(domain.getParameterName())
                .value(domain.getValue());
    }
}
