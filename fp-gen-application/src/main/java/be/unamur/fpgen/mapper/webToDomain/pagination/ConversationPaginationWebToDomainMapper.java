package be.unamur.fpgen.mapper.webToDomain.pagination;


import be.unamur.fpgen.conversation.pagination.ConversationQuery;
import be.unamur.fpgen.conversation.pagination.PagedConversationsQuery;
import be.unamur.fpgen.mapper.webToDomain.MessageTopicWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.PaginationWebToDomainMapper;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.PagedConversationQuery;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class ConversationPaginationWebToDomainMapper {

    public static ConversationQuery map(be.unamur.model.ConversationQuery web) {
        return ConversationQuery.newBuilder()
                .withStartDate(DateUtil.convertLocalDateToOffsetDateTime(web.getStartDate()))
                .withEndDate(DateUtil.convertLocalDateToOffsetDateTime(web.getEndDate()))
                .withMessageTopic(MessageTopicWebToDomainMapper.map(web.getMessageTopic()))
                .withMessageType(MessageTypeWebToDomainMapper.map(web.getMessageType()))
                .withMinInteractionNumber(web.getMinInteractionNumber())
                .withMaxInteractionNumber(web.getMaxInteractionNumber())
                .build();
    }

    public static PagedConversationsQuery map(PagedConversationQuery web) {
        return PagedConversationsQuery.newBuilder()
                .withConversationQuery(map(web.getQuery()))
                .withQueryPage(PaginationWebToDomainMapper.map(web.getPage()))
                .build();
    }
}
