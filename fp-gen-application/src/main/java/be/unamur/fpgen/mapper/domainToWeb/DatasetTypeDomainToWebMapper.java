package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.model.DatasetType;

import java.util.Optional;

public class DatasetTypeDomainToWebMapper {

    public static DatasetType map(final DatasetTypeEnum domain){
        return Optional.ofNullable(domain)
                .map(Enum::name)
                .map(DatasetType::valueOf)
                .orElse(null);
    }
}
