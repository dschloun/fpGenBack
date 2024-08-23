package be.unamur.fpgen.mapper.domainToWeb;

import be.unamur.model.Result;

public class ResultDomainToWebMapper {

    public static Result map(final be.unamur.fpgen.result.Result domain){
        return new Result()
                .id(domain.getId())
                .datasetId(domain.getDataset().getId())
                .datasetName(domain.getDataset().getName())
                .authorId(domain.getAuthor().getId())
                .authorTrigram(domain.getAuthor().getTrigram())
                .experimentDate(domain.getExperimentDate())
                .machineDetails(domain.getMachineDetails())
                .algorithmType(domain.getAlgorithmType())
                .algorithmSettings(domain.getAlgorithmSettingList().stream()
                        .map(AlgorithmSettingDomainToWebMapper::map)
                        .toList())
                .otherSettingDetails(domain.getOtherSettingDetails())
                .accuracy(domain.getAccuracy().doubleValue())
                .precision(domain.getPrecision().doubleValue())
                .recall(domain.getRecall().doubleValue())
                .f1Score(domain.getF1Score().doubleValue())
                .prAuc(domain.getPrAuc().doubleValue())
                .fpRate(domain.getFpRate().doubleValue())
                .fnRate(domain.getFnRate().doubleValue())
                .tpRate(domain.getTpRate().doubleValue())
                .tnRate(domain.getTnRate().doubleValue())
                .appreciation(domain.getAppreciation())
                .comment(domain.getComment());
    }
}