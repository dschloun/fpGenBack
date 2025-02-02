package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.interlocutor.InterlocutorTypeEnum;
import be.unamur.model.Interlocutor;
import be.unamur.model.InterlocutorType;

import java.util.Optional;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class InterlocutorDomainToWebMapper {

    public static Interlocutor map(be.unamur.fpgen.interlocutor.Interlocutor domain){
        return new Interlocutor()
                .id(domain.getId())
                .interlocutorType(mapType(domain.getType()));
    }

    public static InterlocutorType mapType(final InterlocutorTypeEnum domain){
        return Optional.ofNullable(domain)
                .map(Enum::name)
                .map(InterlocutorType::valueOf)
                .orElseThrow();
    }
}
