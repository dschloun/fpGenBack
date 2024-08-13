package be.unamur.fpgen.service;

import be.unamur.fpgen.dataset.AbstractDataset;
import be.unamur.fpgen.dataset.DatasetTypeEnum;
import be.unamur.fpgen.message.MessageTopicEnum;
import be.unamur.fpgen.message.MessageTypeEnum;
import be.unamur.fpgen.repository.StatisticRepository;
import be.unamur.fpgen.statistic.MessageTypeTopicTransformer;
import be.unamur.fpgen.statistic.Statistic;
import be.unamur.fpgen.utils.MapperUtil;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StatisticService {

    private final StatisticRepository statisticRepository;
    private final InstantMessageDatasetService instantMessageDatasetService;
    private final ConversationDatasetService conversationDatasetService;

    public StatisticService(StatisticRepository statisticRepository, InstantMessageDatasetService instantMessageDatasetService, ConversationDatasetService conversationDatasetService) {
        this.statisticRepository = statisticRepository;
        this.instantMessageDatasetService = instantMessageDatasetService;
        this.conversationDatasetService = conversationDatasetService;
    }

    @Async
    public void computeStatistic(final UUID datasetId, final DatasetTypeEnum datasetType) {
        // 1. get dataset
        AbstractDataset dataset;
        if (DatasetTypeEnum.INSTANT_MESSAGE.equals(datasetType)) {
            dataset = instantMessageDatasetService.getDatasetById(datasetId);
        } else {
            dataset = conversationDatasetService.getDatasetById(datasetId);
        }
        // 2. get total
        final Integer total = statisticRepository.findTotal(datasetId);
        // 3. get genuine total
        final Integer genuineTotal = statisticRepository.findGenuineTotal(datasetId);
        // 4. get social engineering total
        final Integer socialEngineeringTotal = statisticRepository.findSocialEngineeringTotal(datasetId);
        // 5. get trolling total
        final Integer trollingTotal = statisticRepository.findTrollingTotal(datasetId);
        // 6. get type topic distribution
        final List<Triple<MessageTypeEnum, MessageTopicEnum, Integer>> distribution = statisticRepository.findTypeTopicDistribution(datasetId);

        // 7. build statistic
        final Statistic statistic = Statistic.newBuilder()
                .withDataset(dataset)
                .withTotal(total)
                .withFakeRatio(BigDecimal.valueOf((trollingTotal + socialEngineeringTotal) / total))
                .withRealRatio(BigDecimal.valueOf(genuineTotal / total))
                .withSocialEngineerRatio(BigDecimal.valueOf(socialEngineeringTotal / total))
                .withTrollRatio(BigDecimal.valueOf(trollingTotal / total))
                .withMessageTopicStatisticList(distribution.stream().map(d -> MessageTypeTopicTransformer.transform(d, total)).collect(Collectors.toSet()))
                .build();

        // 8. save
        statisticRepository
    }

}
