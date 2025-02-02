package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.result.Result;
import be.unamur.fpgen.utils.BigDecimalSafeMapper;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.ResultCreation;
import be.unamur.model.ResultUpdate;

import java.util.stream.Collectors;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class ResultWebToDomainMapper {

    public static Result map(final ResultCreation web){
        return Result.newBuilder()
                .withExperimentDate(DateUtil.convertLocalDateToOffsetDateTime(web.getExperimentDate()))
                .withMachineDetails(web.getMachineDetails())
                .withAlgorithmType(web.getAlgorithmType())
                .withAlgorithmSettingList(web.getAlgorithmSettings().stream()
                        .map(AlgorithmSettingWebToDomainMapper::map)
                        .collect(Collectors.toSet()))
                .withOtherSettingDetails(web.getOtherSettingDetails())
                .withAccuracy(BigDecimalSafeMapper.map(web.getAccuracy()))
                .withPrecision(BigDecimalSafeMapper.map(web.getPrecision()))
                .withRecall(BigDecimalSafeMapper.map(web.getRecall()))
                .withF1Score(BigDecimalSafeMapper.map(web.getF1Score()))
                .withPrAuc(BigDecimalSafeMapper.map(web.getPrAuc()))
                .withFpRate(BigDecimalSafeMapper.map(web.getFpRate()))
                .withFnRate(BigDecimalSafeMapper.map(web.getFnRate()))
                .withTpRate(BigDecimalSafeMapper.map(web.getTpRate()))
                .withTnRate(BigDecimalSafeMapper.map(web.getTnRate()))
                .withAppreciation(web.getAppreciation())
                .withComment(web.getComment())
                .build();
    }

    public static Result map(final ResultUpdate web){
        return Result.newBuilder()
                .withExperimentDate(DateUtil.convertLocalDateToOffsetDateTime(web.getExperimentDate()))
                .withMachineDetails(web.getMachineDetails())
                .withAlgorithmType(web.getAlgorithmType())
                .withAlgorithmSettingList(web.getAlgorithmSettings().stream()
                        .map(AlgorithmSettingWebToDomainMapper::map)
                        .collect(Collectors.toSet()))
                .withOtherSettingDetails(web.getOtherSettingDetails())
                .withAccuracy(BigDecimalSafeMapper.map(web.getAccuracy()))
                .withPrecision(BigDecimalSafeMapper.map(web.getPrecision()))
                .withRecall(BigDecimalSafeMapper.map(web.getRecall()))
                .withF1Score(BigDecimalSafeMapper.map(web.getF1Score()))
                .withPrAuc(BigDecimalSafeMapper.map(web.getPrAuc()))
                .withFpRate(BigDecimalSafeMapper.map(web.getFpRate()))
                .withFnRate(BigDecimalSafeMapper.map(web.getFnRate()))
                .withTpRate(BigDecimalSafeMapper.map(web.getTpRate()))
                .withTnRate(BigDecimalSafeMapper.map(web.getTnRate()))
                .withAppreciation(web.getAppreciation())
                .withComment(web.getComment())
                .build();
    }
}
