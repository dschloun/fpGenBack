package be.unamur.fpgen.service;

import be.unamur.fpgen.message.pagination.conversation_message.ConversationMessagesPage;
import be.unamur.fpgen.message.pagination.conversation_message.PagedConversationMessagesQuery;
import be.unamur.fpgen.repository.ConversationMessageRepository;
import be.unamur.fpgen.utils.DateUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Service class for managing conversation messages.
 */
@Service
public class ConversationMessageService {
    private final ConversationMessageRepository conversationMessageRepository;

    public ConversationMessageService(ConversationMessageRepository conversationMessageRepository) {
        this.conversationMessageRepository = conversationMessageRepository;
    }

    /**
     * Search conversation messages with pagination.
     *
     * @param query the query to search conversation messages.
     * @return the conversation messages page.
     */
    @Transactional
    public ConversationMessagesPage searchConversationMessagePaginate(final PagedConversationMessagesQuery query){
        final Pageable pageable = PageRequest
                .of(query.getQueryPage().getPage(),
                        query.getQueryPage().getSize());

        return conversationMessageRepository.findPagination(
                query.getConversationMessageQuery().getTopic(),
                query.getConversationMessageQuery().getType(),
                query.getConversationMessageQuery().getContent(),
                DateUtil.ifNullReturnOldDate(query.getConversationMessageQuery().getStartDate()),
                DateUtil.ifNullReturnTomorrow(query.getConversationMessageQuery().getEndDate()),
                pageable);
    }
}
