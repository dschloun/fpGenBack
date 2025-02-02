package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.dataset.DatasetFunctionEnum;

import java.util.Optional;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class DatasetWebToDomainMapper {

    public static DatasetFunctionEnum mapFunction(final be.unamur.model.DatasetFunctionEnum web){
        return Optional.ofNullable(web)
                .map(Enum::name)
                .map(DatasetFunctionEnum::valueOf)
                .orElseThrow();
    }
}
