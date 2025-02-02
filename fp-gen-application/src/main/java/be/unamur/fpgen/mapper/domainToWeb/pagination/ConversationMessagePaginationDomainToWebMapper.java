package be.unamur.fpgen.mapper.domainToWeb.pagination;

import be.unamur.fpgen.mapper.domainToWeb.ConversationMessageDomainToWebMapper;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.ConversationMessagesPage;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class ConversationMessagePaginationDomainToWebMapper {

    public static ConversationMessagesPage map(be.unamur.fpgen.message.pagination.conversation_message.ConversationMessagesPage domain) {
        return new ConversationMessagesPage()
                .conversationMessages(MapperUtil.mapList(domain.getConversationMessageList(), ConversationMessageDomainToWebMapper::map))
                .pagination(PaginationDomainToWebMapper.map(domain.getPagination()));
    }
}
