package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.model.GenerationType;

import java.util.Optional;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class GenerationTypeWebToDomainMapper {
    public static GenerationTypeEnum map(GenerationType web){
        return Optional.ofNullable(web)
                .map(Enum::name)
                .map(GenerationTypeEnum::valueOf)
                .orElse(null);
    }
}
