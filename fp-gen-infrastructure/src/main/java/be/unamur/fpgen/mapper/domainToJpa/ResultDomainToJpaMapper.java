package be.unamur.fpgen.mapper.domainToJpa;

import be.unamur.fpgen.entity.author.AuthorEntity;
import be.unamur.fpgen.entity.dataset.DatasetEntity;
import be.unamur.fpgen.entity.result.ResultEntity;
import be.unamur.fpgen.result.Result;
import be.unamur.fpgen.utils.MapperUtil;

/**
 * This class is used to map the domain to the JPA entity.
 */
public class ResultDomainToJpaMapper {

    public static ResultEntity map(final Result domain, final DatasetEntity dataset, final AuthorEntity author){
        final ResultEntity entity = new ResultEntity();
        entity.setId(domain.getId());
        entity.setDataset(dataset);
        entity.setAuthor(author);
        entity.setExperimentDate(domain.getExperimentDate());
        entity.setMachineDetails(domain.getMachineDetails());
        entity.setAlgorithmType(domain.getAlgorithmType());
        entity.setOtherSettingDetails(domain.getOtherSettingDetails());
        entity.setAccuracy(domain.getAccuracy());
        entity.setPrecision(domain.getPrecision());
        entity.setRecall(domain.getRecall());
        entity.setF1Score(domain.getF1Score());
        entity.setPrAuc(domain.getPrAuc());
        entity.setFpRate(domain.getFpRate());
        entity.setFnRate(domain.getFnRate());
        entity.setTpRate(domain.getTpRate());
        entity.setTnRate(domain.getTnRate());
        entity.setAppreciation(domain.getAppreciation());
        entity.setComment(domain.getComment());
        return entity.addAlgorithmSetting(MapperUtil.mapSet(domain.getAlgorithmSettingList(), i -> AlgorithmSettingDomainToJpaMapper.map(i, entity)));

    }

    public static ResultEntity mapForUpdate(final Result domain, final ResultEntity entity){
        entity.setExperimentDate(domain.getExperimentDate());
        entity.setMachineDetails(domain.getMachineDetails());
        entity.setAlgorithmType(domain.getAlgorithmType());
        entity.setOtherSettingDetails(domain.getOtherSettingDetails());
        entity.setAccuracy(domain.getAccuracy());
        entity.setPrecision(domain.getPrecision());
        entity.setRecall(domain.getRecall());
        entity.setF1Score(domain.getF1Score());
        entity.setPrAuc(domain.getPrAuc());
        entity.setFpRate(domain.getFpRate());
        entity.setFnRate(domain.getFnRate());
        entity.setTpRate(domain.getTpRate());
        entity.setTnRate(domain.getTnRate());
        entity.setAppreciation(domain.getAppreciation());
        entity.setComment(domain.getComment());
        return entity.addAlgorithmSetting(MapperUtil.mapSet(domain.getAlgorithmSettingList(), i -> AlgorithmSettingDomainToJpaMapper.map(i, entity)));
    }
}

