package be.unamur.fpgen.mapper.webToDomain.pagination;

import be.unamur.fpgen.message.pagination.InstantMessage.InstantMessageQuery;
import be.unamur.fpgen.message.pagination.InstantMessage.PagedInstantMessagesQuery;
import be.unamur.fpgen.mapper.webToDomain.MessageTopicWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.PaginationWebToDomainMapper;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.PagedInstantMessageQuery;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class InstantMessagePaginationWebToDomainMapper {

    public static InstantMessageQuery map(be.unamur.model.InstantMessageQuery web) {
        return InstantMessageQuery.newBuilder()
                .withStartDate(DateUtil.convertLocalDateToOffsetDateTime(web.getStartDate()))
                .withEndDate(DateUtil.convertLocalDateToOffsetDateTime(web.getEndDate()))
                .withMessageTopic(MessageTopicWebToDomainMapper.map(web.getMessageTopic()))
                .withMessageType(MessageTypeWebToDomainMapper.map(web.getMessageType()))
                .withContent(web.getContent())
                .build();
    }

    public static PagedInstantMessagesQuery map(PagedInstantMessageQuery web) {
        return PagedInstantMessagesQuery.newBuilder()
                .withInstantMessageQuery(map(web.getQuery()))
                .withQueryPage(PaginationWebToDomainMapper.map(web.getPage()))
                .build();
    }
}
