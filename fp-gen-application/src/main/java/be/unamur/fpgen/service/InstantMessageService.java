package be.unamur.fpgen.service;

import be.unamur.fpgen.exception.InstantMessageNotFoundException;
import be.unamur.fpgen.generation.InstantMessageGeneration;
import be.unamur.fpgen.message.InstantMessage;
import be.unamur.fpgen.message.pagination.InstantMessage.InstantMessagesPage;
import be.unamur.fpgen.message.pagination.InstantMessage.PagedInstantMessagesQuery;
import be.unamur.fpgen.mapper.webToDomain.InstantMessageWebToDomainMapper;
import be.unamur.fpgen.repository.InstantMessageRepository;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.InstantMessageBatchCreation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InstantMessageService {

    private final InstantMessageRepository instantMessageRepository;
    private final InstantMessageGenerationService instantMessageGenerationService;

    public InstantMessageService(final InstantMessageRepository instantMessageRepository,
                                 final InstantMessageGenerationService instantMessageGenerationService) {
        this.instantMessageRepository = instantMessageRepository;
        this.instantMessageGenerationService = instantMessageGenerationService;
    }

    @Transactional
    public void generateInstantMessages(final InstantMessageBatchCreation command) {
        // 0. for each
        command.getInstantMessageCreationList().forEach(imc -> {
            // 1. create generation data
            final InstantMessageGeneration generation = instantMessageGenerationService.createInstantMessageGeneration(imc, command.getAuthorId());

            // 2. prepare a list of instant messages
            final List<InstantMessage> instantMessageList = new ArrayList<>();

            // 3. generate instant messages
            //todo call chat gpt api with prompt // return the x messages in json format, unmarshall, ...
            for(int i = 0; i < imc.getQuantity(); i++){
                instantMessageList.add(InstantMessageWebToDomainMapper.mapForCreate(imc, String.format("content %s", i)));
            }
            // 4. save the instant messages
            List<InstantMessage> saved = instantMessageRepository.saveInstantMessageList(instantMessageList, generation);
        });
    }

    @Transactional
    public InstantMessage getInstantMessageById(UUID instantMessageId) {
        return instantMessageRepository.getInstantMessageById(instantMessageId)
                .orElseThrow(() -> InstantMessageNotFoundException.withId(instantMessageId));
    }

    @Transactional
    public InstantMessagesPage searchInstantMessagesPaginate(final PagedInstantMessagesQuery query) {
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());

        return instantMessageRepository.findPagination(
                query.getInstantMessageQuery().getMessageTopic(),
                query.getInstantMessageQuery().getMessageType(),
                query.getInstantMessageQuery().getContent(),
                DateUtil.ifNullReturnOldDate(query.getInstantMessageQuery().getStartDate()),
                DateUtil.ifNullReturnTomorrow(query.getInstantMessageQuery().getEndDate()),
                pageable);
    }

    @Transactional
    public void deleteById(UUID instantMessageId) {
        instantMessageRepository.deleteInstantMessageById(instantMessageId);
    }

}
