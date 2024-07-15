package be.unamur.fpgen.service;

import be.unamur.fpgen.generation.AbstractGeneration;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.instant_message.InstantMessage;
import be.unamur.fpgen.mapper.InstantMessageWebToDomainMapper;
import be.unamur.fpgen.repository.InstantMessageRepository;
import be.unamur.model.GenerationCreation;
import be.unamur.model.InstantMessageBatchCreation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class InstantMessageBatchGenerationService {

    private final InstantMessageRepository instantMessageRepository;
    private final SaveGenerationService saveGenerationService;

    public InstantMessageBatchGenerationService(final InstantMessageRepository instantMessageRepository,
                                                final SaveGenerationService saveGenerationService) {
        this.instantMessageRepository = instantMessageRepository;
        this.saveGenerationService = saveGenerationService;
    }

    @Transactional
    public void generateInstantMessages(final InstantMessageBatchCreation command) {
        // 0. for each
        command.getInstantMessageCreationList().forEach(imc -> {
            // 1. create generation data
            final InstantMessageGeneration generation = saveGenerationService.createInstantMessageGeneration(imc);

            // 2. prepare a list of instant messages
            final List<InstantMessage> instantMessageList = new ArrayList<>();

            // 3. generate instant messages
            //todo call chat gpt api with prompt // return the x messages in json format, unmarshall, ...
            for(int i = 0; i < imc.getQuantity(); i++){
                instantMessageList.add(InstantMessageWebToDomainMapper.mapForCreate(imc, String.format("content %s", i)));
            }
            // 4. save the instant messages
            List<InstantMessage> saved = instantMessageRepository.saveInstantMessageList(instantMessageList, generation);

            System.out.println("test");
        });
    }


}
