package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.fpgen.utils.BigDecimalSafeMapper;
import be.unamur.model.Result;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class ResultDomainToWebMapper {

    public static Result map(final be.unamur.fpgen.result.Result domain){
        return new Result()
                .id(domain.getId())
                .datasetId(domain.getDataset().getId())
                .datasetName(domain.getDataset().getName())
                .author(AuthorDomainToWebMapper.map(domain.getAuthor()))
                .experimentDate(domain.getExperimentDate())
                .machineDetails(domain.getMachineDetails())
                .algorithmType(domain.getAlgorithmType())
                .algorithmSettings(domain.getAlgorithmSettingList().stream()
                        .map(AlgorithmSettingDomainToWebMapper::map)
                        .toList())
                .otherSettingDetails(domain.getOtherSettingDetails())
                .accuracy(BigDecimalSafeMapper.map(domain.getAccuracy()))
                .precision(BigDecimalSafeMapper.map(domain.getPrecision()))
                .recall(BigDecimalSafeMapper.map(domain.getRecall()))
                .f1Score(BigDecimalSafeMapper.map(domain.getF1Score()))
                .prAuc(BigDecimalSafeMapper.map(domain.getPrAuc()))
                .fpRate(BigDecimalSafeMapper.map(domain.getFpRate()))
                .fnRate(BigDecimalSafeMapper.map(domain.getFnRate()))
                .tpRate(BigDecimalSafeMapper.map(domain.getTpRate()))
                .tnRate(BigDecimalSafeMapper.map(domain.getTnRate()))
                .appreciation(domain.getAppreciation())
                .comment(domain.getComment());
    }
}