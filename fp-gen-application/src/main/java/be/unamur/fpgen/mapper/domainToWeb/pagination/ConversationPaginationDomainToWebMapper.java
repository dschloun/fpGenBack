package be.unamur.fpgen.mapper.domainToWeb.pagination;

import be.unamur.fpgen.mapper.domainToWeb.ConversationDomainToWebMapper;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.ConversationsPage;

public class ConversationPaginationDomainToWebMapper {

    public static ConversationsPage map(be.unamur.fpgen.conversation.pagination.ConversationsPage domain) {
        return new ConversationsPage()
                .conversations(MapperUtil.mapList(domain.getConversationList(), ConversationDomainToWebMapper::mapSummary))
                .pagination(PaginationDomainToWebMapper.map(domain.getPagination()));
    }
}
