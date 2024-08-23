package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.author.Author;
import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.result.Result;
import be.unamur.model.ResultCreation;
import be.unamur.model.ResultUpdate;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class ResultWebToDomainMapper {

    public static Result map(final ResultCreation web){
        return Result.newBuilder()
                .withDataset(Dataset.newBuilder().withId(web.getDatasetId()).build())
                .withAuthor(Author.newBuilder().withId(web.getAuthorId()).build())
                .withExperimentDate(web.getExperimentDate())
                .withMachineDetails(web.getMachineDetails())
                .withAlgorithmType(web.getAlgorithmType())
                .withAlgorithmSettingList(web.getAlgorithmSettings().stream()
                        .map(AlgorithmSettingWebToDomainMapper::map)
                        .collect(Collectors.toSet()))
                .withOtherSettingDetails(web.getOtherSettingDetails())
                .withAccuracy(BigDecimal.valueOf(web.getAccuracy()))
                .withPrecision(BigDecimal.valueOf(web.getPrecision()))
                .withRecall(BigDecimal.valueOf(web.getRecall()))
                .withF1Score(BigDecimal.valueOf(web.getF1Score()))
                .withPrAuc(BigDecimal.valueOf(web.getPrAuc()))
                .withFpRate(BigDecimal.valueOf(web.getFpRate()))
                .withFnRate(BigDecimal.valueOf(web.getFnRate()))
                .withTpRate(BigDecimal.valueOf(web.getTpRate()))
                .withTnRate(BigDecimal.valueOf(web.getTnRate()))
                .withAppreciation(web.getAppreciation())
                .withComment(web.getComment())
                .build();
    }

    public static Result map(final ResultUpdate web){
        return Result.newBuilder()
                .withExperimentDate(web.getExperimentDate())
                .withMachineDetails(web.getMachineDetails())
                .withAlgorithmType(web.getAlgorithmType())
                .withAlgorithmSettingList(web.getAlgorithmSettings().stream()
                        .map(AlgorithmSettingWebToDomainMapper::map)
                        .collect(Collectors.toSet()))
                .withOtherSettingDetails(web.getOtherSettingDetails())
                .withAccuracy(BigDecimal.valueOf(web.getAccuracy()))
                .withPrecision(BigDecimal.valueOf(web.getPrecision()))
                .withRecall(BigDecimal.valueOf(web.getRecall()))
                .withF1Score(BigDecimal.valueOf(web.getF1Score()))
                .withPrAuc(BigDecimal.valueOf(web.getPrAuc()))
                .withFpRate(BigDecimal.valueOf(web.getFpRate()))
                .withFnRate(BigDecimal.valueOf(web.getFnRate()))
                .withTpRate(BigDecimal.valueOf(web.getTpRate()))
                .withTnRate(BigDecimal.valueOf(web.getTnRate()))
                .withAppreciation(web.getAppreciation())
                .withComment(web.getComment())
                .build();
    }
}