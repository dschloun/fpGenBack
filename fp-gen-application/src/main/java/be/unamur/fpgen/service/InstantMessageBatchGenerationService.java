package be.unamur.fpgen.service;

import be.unamur.fpgen.generation.Generation;
import be.unamur.fpgen.generation.GenerationTypeEnum;
import be.unamur.fpgen.instant_message.InstantMessage;
import be.unamur.fpgen.mapper.InstantMessageWebToDomainMapper;
import be.unamur.fpgen.repository.InstantMessageRepository;
import be.unamur.model.InstantMessageBatchCreation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class InstantMessageBatchGenerationService {

    private final InstantMessageRepository instantMessageRepository;

    public InstantMessageBatchGenerationService(InstantMessageRepository instantMessageRepository) {
        this.instantMessageRepository = instantMessageRepository;
    }

    @Transactional
    public void generateInstantMessages(final InstantMessageBatchCreation instantMessageBatchCreation) {
        // 0. create generation data
        //todo in future let generation data in the input (modif api)
        final Generation generation = Generation.newBuilder()
                .withAuthorTrigram("DSC")
                .withDetails("details chat gpt prompt")
                .withType(GenerationTypeEnum.IMB)
                .withBatch(true)
                .build();

        // 1. prepare a list of instant messages
        final List<InstantMessage> instantMessageList = new ArrayList<>();

        // 2. generate instant messages
        //todo call chat gpt api with prompt
        instantMessageBatchCreation.getInstantMessageCreationList()
                .forEach(imc -> {
                    for(int i = 0; i < imc.getQuantity(); i++){
                        instantMessageList.add(InstantMessageWebToDomainMapper.mapForCreate(imc, String.format("content %s", i)));
                    }
                });

        List<InstantMessage> saved = instantMessageRepository.saveInstantMessageList(instantMessageList, generation);
        System.out.println("test");
    }


}
