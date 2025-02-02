package be.unamur.fpgen.messaging.listener;

import be.unamur.fpgen.dataset.Dataset;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.messaging.event.DatasetOngoingGenerationCleanEvent;
import be.unamur.fpgen.messaging.event.StatisticComputationEvent;
import be.unamur.fpgen.repository.StatisticRepository;
import be.unamur.fpgen.service.DatasetService;
import be.unamur.fpgen.statistic.MessageTypeTopicTransformer;
import be.unamur.fpgen.statistic.Statistic;
import be.unamur.fpgen.statistic.TypeTopicDistributionProjection;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Listener that listens to {@link StatisticComputationEvent} and computes the statistic for the dataset when requested.
 */
@Component
public class StatisticComputationListener {

    private final StatisticRepository statisticRepository;
    private final DatasetService datasetService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public StatisticComputationListener(final StatisticRepository statisticRepository,
                                        final DatasetService datasetService,
                                        final ApplicationEventPublisher applicationEventPublisher) {
        this.statisticRepository = statisticRepository;
        this.datasetService = datasetService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Method that computes the statistic for the dataset.
     *
     * @param event the event that contains the dataset id for which the statistic should be computed.
     */
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void computeStatistic(final StatisticComputationEvent event) {
        // 1. get dataset
        Dataset dataset = datasetService.findById(event.getDatasetId());
        // 2. delete existing statistic if present
        statisticRepository.findStatisticByDatasetId(event.getDatasetId()).ifPresent(s -> statisticRepository.deleteByDataset(dataset));
        // 3. get total
        final Integer total = statisticRepository.findTotal(event.getDatasetId());
        // 4. get genuine total
        final Integer genuineTotal = statisticRepository.findGenuineTotal(event.getDatasetId());
        // 5. get social engineering total
        final Integer socialEngineeringTotal = statisticRepository.findSocialEngineeringTotal(event.getDatasetId());
        // 6. get harassment total
        final Integer harassmentTotal = statisticRepository.findHarassmentTotal(event.getDatasetId());
        // 7. get type topic distribution
        final List<TypeTopicDistributionProjection> distribution = statisticRepository.findTypeTopicDistribution(event.getDatasetId());

        // 8. build statistic
        final Statistic statistic = Statistic.newBuilder()
                .withDataset(dataset)
                .withTotal(total)
                .withFakeRatio(BigDecimal.valueOf(harassmentTotal).add(BigDecimal.valueOf(socialEngineeringTotal)).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP))
                .withRealRatio(BigDecimal.valueOf(genuineTotal).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP))
                .withSocialEngineerRatio(BigDecimal.valueOf(socialEngineeringTotal).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP))
                .withHarasserRatio(BigDecimal.valueOf(harassmentTotal).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP))
                .withGenuineTopicStatisticList(distribution.stream().filter(d -> MessageTypeEnum.GENUINE.equals(d.getType())).map(d -> MessageTypeTopicTransformer.transform(d, genuineTotal)).collect(Collectors.toSet()))
                .withSocialEngineeringTopicStatisticList(distribution.stream().filter(d -> MessageTypeEnum.SOCIAL_ENGINEERING.equals(d.getType())).map(d -> MessageTypeTopicTransformer.transform(d, socialEngineeringTotal)).collect(Collectors.toSet()))
                .withHarassmentTopicStatisticList(distribution.stream().filter(d -> MessageTypeEnum.HARASSMENT.equals(d.getType())).map(d -> MessageTypeTopicTransformer.transform(d, harassmentTotal)).collect(Collectors.toSet()))
                .build();

        // 9. save
        statisticRepository.save(statistic, dataset);

        // 10. remove ongoing generation from dataset if exists
        if (Objects.nonNull(dataset.getOngoingGenerationId())){
            applicationEventPublisher.publishEvent(new DatasetOngoingGenerationCleanEvent(this, dataset.getId()));
        }
    }
}
