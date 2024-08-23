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

Result:
type: object
required:
        - datasetId
        - authorId
properties:
id:
$ref: 'common.yaml#/components/schemas/UUID'
datasetId:
$ref: 'common.yaml#/components/schemas/UUID'
datasetName:
type: string
authorId:
$ref: 'common.yaml#/components/schemas/UUID'
authorTrigram:
type: string
experimentDate:
$ref: 'common.yaml#/components/schemas/DateTime'
machineDetails:
type: string
algorithmType:
type: string
algorithmSettings:
type: array
items:
$ref: '#/components/schemas/AlgorithmSetting'
otherSettingDetails:
type: string
accuracy:
type: number
format: double
precision:
type: number
format: double
recall:
type: number
format: double
f1Score:
type: number
format: double
prAuc:
type: number
format: double
fpRate:
type: number
format: double
fnRate:
type: number
format: double
tpRate:
type: number
format: double
tnRate:
type: number
format: double
appreciation:
type: string
comment:
type: string
