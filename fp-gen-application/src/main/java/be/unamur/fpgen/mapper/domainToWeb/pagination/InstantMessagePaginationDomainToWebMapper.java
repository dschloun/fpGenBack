package be.unamur.fpgen.mapper.domainToWeb.pagination;

import be.unamur.fpgen.mapper.domainToWeb.InstantMessageDomainToWebMapper;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.InstantMessagesPage;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class InstantMessagePaginationDomainToWebMapper {

    public static InstantMessagesPage map(be.unamur.fpgen.message.pagination.InstantMessage.InstantMessagesPage domain) {
        return new InstantMessagesPage()
                .instantMessages(MapperUtil.mapList(domain.getInstantMessageList(), InstantMessageDomainToWebMapper::map))
                .pagination(PaginationDomainToWebMapper.map(domain.getPagination()));
    }
}
