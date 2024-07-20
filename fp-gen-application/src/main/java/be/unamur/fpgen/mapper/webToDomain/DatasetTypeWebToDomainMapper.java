package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.model.DatasetType;

import java.util.Optional;

public class DatasetTypeWebToDomainMapper {

    public static DatasetTypeEnum map(final DatasetType web){
        return Optional.ofNullable(web)
                .map(Enum::name)
                .map(DatasetTypeEnum::valueOf)
                .orElse(null);
    }
}
