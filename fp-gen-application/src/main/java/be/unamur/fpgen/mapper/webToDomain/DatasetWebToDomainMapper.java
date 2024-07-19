package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.dataset.DatasetFunctionEnum;
import be.unamur.model.DatasetType;

import java.util.Optional;

public class DatasetWebToDomainMapper {

    public static DatasetFunctionEnum mapFunction(final DatasetType web){
        return Optional.ofNullable(web)
                .map(Enum::name)
                .map(DatasetFunctionEnum::valueOf)
                .orElseThrow();
    }
}
