package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.project.TrainingTestDifference;
import be.unamur.model.TrainingTestDifferences;
import be.unamur.model.TypeTopicDifference;

import java.util.Comparator;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class TrainingTestDifferenceDomainToWebMapper {

    public static TypeTopicDifference map(final be.unamur.fpgen.project.TypeTopicDifference domain){
        return new TypeTopicDifference()
                .messageType(MessageTypeDomainToWebMapper.map(domain.getType()))
                .messageTopic(MessageTopicDomainToWebMapper.map(domain.getTopic()))
                .difference(domain.getDifference());
    }

    public static TrainingTestDifferences map(final TrainingTestDifference domain){
        return new TrainingTestDifferences()
                .fakeDifference(domain.getFakeDifference())
                .realDifference(domain.getRealDifference())
                .socialEngineeringDifference(domain.getSocialEngineeringDifference())
                .harassmentDifference(domain.getHarassmentDifference())
                .typeTopicDifferences(domain.getTypeTopicDifferences()
                        .stream()
                        .sorted(Comparator.comparing(be.unamur.fpgen.project.TypeTopicDifference::getKey))
                        .map(TrainingTestDifferenceDomainToWebMapper::map)
                        .toList());
    }
}
