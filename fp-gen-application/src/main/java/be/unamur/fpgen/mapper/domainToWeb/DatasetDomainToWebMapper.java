package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.dataset.DatasetFunctionEnum;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class DatasetDomainToWebMapper {

    public static be.unamur.model.DatasetFunctionEnum mapFunction(final DatasetFunctionEnum domain){
        return Optional.ofNullable(domain)
                .map(Enum::name)
                .map(be.unamur.model.DatasetFunctionEnum::valueOf)
                .orElseThrow();
    }

    public static be.unamur.model.Dataset map(final Dataset domain){
        return new be.unamur.model.Dataset()
                .id(domain.getId())
                .creationDate(domain.getCreationDate())
                .modificationDate(domain.getModificationDate())
                .author(AuthorDomainToWebMapper.map(domain.getAuthor()))
                .datasetFunction(mapFunction(domain.getDatasetFunction()))
                .comment(domain.getComment())
                .description(domain.getDescription())
                .version(BigDecimal.valueOf(domain.getVersion()))
                .name(domain.getName())
                .type(DatasetTypeDomainToWebMapper.map(domain.getType()))
                .ongoingGenerationId(domain.getOngoingGenerationId())
                .statistic(StatisticDomainToWebMapper.map(domain.getStatistic()))
                .validated(domain.isValidated())
                .isLastVersion(domain.isLastVersion())
                .hasResult(domain.hasResult())
                .recordNumber(domain.getRecordNumber());
    }
}
