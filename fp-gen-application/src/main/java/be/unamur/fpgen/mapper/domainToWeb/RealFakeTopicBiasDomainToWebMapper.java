package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.model.RealFakeTopicBias;

public class RealFakeTopicBiasDomainToWebMapper {

    public static RealFakeTopicBias map(final be.unamur.fpgen.dataset.RealFakeTopicBias domain){
        return new RealFakeTopicBias()
                .messageTopic(MessageTopicDomainToWebMapper.map(domain.getTopic()))
                .realNumber(domain.getRealNumber())
                .fakeNumber(domain.getFakeNumber())
                .bias(domain.getBias());
    }
}
