package be.unamur.fpgen.mapper.jpaToDomain;

import be.unamur.fpgen.entity.result.ResultEntity;
import be.unamur.fpgen.result.Result;
import be.unamur.fpgen.utils.MapperUtil;

import java.util.Objects;

/**
 * This class is used to map the JPA entity to the domain entity.
 */
public class ResultJpaToDomainMapper {

    public static final Result map(final ResultEntity entity){
        if (Objects.isNull(entity)){
            return null;
        }

        return Result.newBuilder()
                .withId(entity.getId())
                .withCreationDate(entity.getCreationDate())
                .withModificationDate(entity.getModificationDate())
                .withDataset(DatasetJpaToDomainMapper.map(entity.getDataset()))
                .withAuthor(AuthorJpaToDomainMapper.map(entity.getAuthor()))
                .withExperimentDate(entity.getExperimentDate())
                .withMachineDetails(entity.getMachineDetails())
                .withAlgorithmType(entity.getAlgorithmType())
                .withAlgorithmSettingList(MapperUtil.mapSet(entity.getAlgorithmSettingList(), AlgorithmSettingJpaToDomainMapper::map))
                .withOtherSettingDetails(entity.getOtherSettingDetails())
                .withAccuracy(entity.getAccuracy())
                .withPrecision(entity.getPrecision())
                .withRecall(entity.getRecall())
                .withF1Score(entity.getF1Score())
                .withPrAuc(entity.getPrAuc())
                .withFpRate(entity.getFpRate())
                .withFnRate(entity.getFnRate())
                .withTpRate(entity.getTpRate())
                .withTnRate(entity.getTnRate())
                .withAppreciation(entity.getAppreciation())
                .withComment(entity.getComment())
                .build();
    }
}
