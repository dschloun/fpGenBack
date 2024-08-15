package be.unamur.fpgen.messaging;

import be.unamur.fpgen.dataset.AbstractDataset;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.repository.StatisticRepository;
import be.unamur.fpgen.service.ConversationDatasetService;
import be.unamur.fpgen.service.InstantMessageDatasetService;
import be.unamur.fpgen.statistic.MessageTypeTopicTransformer;
import be.unamur.fpgen.statistic.Statistic;
import be.unamur.fpgen.statistic.TypeTopicDistributionProjection;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatisticComputationListener {

    private final StatisticRepository statisticRepository;
    private final InstantMessageDatasetService instantMessageDatasetService;
    private final ConversationDatasetService conversationDatasetService;

    public StatisticComputationListener(StatisticRepository statisticRepository, InstantMessageDatasetService instantMessageDatasetService, ConversationDatasetService conversationDatasetService) {
        this.statisticRepository = statisticRepository;
        this.instantMessageDatasetService = instantMessageDatasetService;
        this.conversationDatasetService = conversationDatasetService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void computeStatistic(final StatisticComputationEvent event) {
        // 1. get dataset
        AbstractDataset dataset;
        if (DatasetTypeEnum.INSTANT_MESSAGE.equals(event.getDatasetType())) {
            dataset = instantMessageDatasetService.getDatasetById(event.getDatasetId());
        } else {
            dataset = conversationDatasetService.getDatasetById(event.getDatasetId());
        }
        // 2. delete existing statistic if present
        statisticRepository.findStatisticByDatasetId(event.getDatasetId()).ifPresent(s -> statisticRepository.deleteByDataset(dataset));
        // 3. get total
        final Integer total = statisticRepository.findTotal(event.getDatasetId());
        // 4. get genuine total
        final Integer genuineTotal = statisticRepository.findGenuineTotal(event.getDatasetId());
        // 5. get social engineering total
        final Integer socialEngineeringTotal = statisticRepository.findSocialEngineeringTotal(event.getDatasetId());
        // 6. get trolling total
        final Integer trollingTotal = statisticRepository.findTrollingTotal(event.getDatasetId());
        // 7. get type topic distribution
        final List<TypeTopicDistributionProjection> distribution = statisticRepository.findTypeTopicDistribution(event.getDatasetId());

        // 8. build statistic
        final Statistic statistic = Statistic.newBuilder()
                .withDataset(dataset)
                .withTotal(total)
                .withFakeRatio(BigDecimal.valueOf(trollingTotal).add(BigDecimal.valueOf(socialEngineeringTotal)).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP))
                .withRealRatio(BigDecimal.valueOf(genuineTotal).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP))
                .withSocialEngineerRatio(BigDecimal.valueOf(socialEngineeringTotal).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP))
                .withTrollRatio(BigDecimal.valueOf(trollingTotal).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP))
                .withMessageTopicStatisticList(distribution.stream().map(d -> MessageTypeTopicTransformer.transform(d, total)).collect(Collectors.toSet()))
                .build();

        // 9. save
        statisticRepository.save(statistic, dataset);
    }

}
