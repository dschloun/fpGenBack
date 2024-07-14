package be.unamur.fpgen.service;

import be.unamur.fpgen.generation.AbstractGeneration;
import be.unamur.fpgen.repository.GenerationRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.InstantMessageBatchCreation;
import be.unamur.model.InstantMessageCreation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Service
public class SaveGenerationService {
    private final GenerationRepository generationRepository;

    public SaveGenerationService(final GenerationRepository generationRepository) {
        this.generationRepository = generationRepository;
    }

    @Transactional
    public AbstractGeneration createInstantMessageGeneration(final InstantMessageBatchCreation command){
        return generationRepository.saveInstantMessageGeneration(
                AbstractGeneration.newBuilder()
                        .withAuthorTrigram("DSC")
                        .withDetails(getDetails(command))
                        .withType(command.getInstantMessageCreationList().size() > 1 ? GenerationTypeEnum.IMB : GenerationTypeEnum.IM)
                        .withBatch(command.getInstantMessageCreationList().get(0).getQuantity() > 1)
                        .build());
    }

    private String getDetails(final InstantMessageBatchCreation command){
        final String begin = "generate instant message set [\n";
        final String end = String.format("\n], author: %s \n date: %s", "DSC", DateUtil.convertOffsetDateTimeToString(OffsetDateTime.now()));
        final StringBuilder loopText = new StringBuilder();
        command.getInstantMessageCreationList()
                .forEach(t -> {
                    loopText.append(getSingleBatchDetail(t));
                });
        return begin + loopText + end;
    }

    private String getSingleBatchDetail(final InstantMessageCreation command){
        return String.format("{Topic: %s, Type: %s, Quantity: %s}\s", command.getMessageTopic(), command.getMessageType(), command.getQuantity());
    }
}
