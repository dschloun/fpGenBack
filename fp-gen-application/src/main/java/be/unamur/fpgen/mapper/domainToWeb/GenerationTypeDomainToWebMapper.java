package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.model.GenerationType;

import java.util.Optional;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class GenerationTypeDomainToWebMapper {
    public static GenerationType map(GenerationTypeEnum web){
        return Optional.ofNullable(web)
                .map(Enum::name)
                .map(GenerationType::valueOf)
                .orElse(null);
    }
}
