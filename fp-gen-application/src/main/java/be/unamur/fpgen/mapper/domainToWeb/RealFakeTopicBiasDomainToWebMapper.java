package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.model.RealFakeTopicBias;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class RealFakeTopicBiasDomainToWebMapper {

    public static RealFakeTopicBias map(final be.unamur.fpgen.dataset.RealFakeTopicBias domain){
        return new RealFakeTopicBias()
                .messageTopic(MessageTopicDomainToWebMapper.map(domain.getTopic()))
                .realNumber(domain.getRealNumber())
                .fakeNumber(domain.getFakeNumber())
                .bias(domain.getBias());
    }
}
