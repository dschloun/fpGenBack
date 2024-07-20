package be.unamur.fpgen.mapper.webToDomain;

import be.unamur.fpgen.instant_message.pagination.InstantMessageQuery;
import be.unamur.fpgen.instant_message.pagination.PagedInstantMessagesQuery;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.PagedInstantMessageQuery;

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
