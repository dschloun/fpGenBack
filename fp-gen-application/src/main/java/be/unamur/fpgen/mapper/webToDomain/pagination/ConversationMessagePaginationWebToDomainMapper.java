package be.unamur.fpgen.mapper.webToDomain.pagination;

import be.unamur.fpgen.message.pagination.conversation_message.ConversationMessageQuery;
import be.unamur.fpgen.message.pagination.conversation_message.PagedConversationMessagesQuery;
import be.unamur.fpgen.mapper.webToDomain.MessageTopicWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.PaginationWebToDomainMapper;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.PagedConversationMessageQuery;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class ConversationMessagePaginationWebToDomainMapper {

    public static ConversationMessageQuery map(be.unamur.model.ConversationMessageQuery web) {
        return ConversationMessageQuery.newBuilder()
                .withStartDate(DateUtil.convertLocalDateToOffsetDateTime(web.getStartDate()))
                .withEndDate(DateUtil.convertLocalDateToOffsetDateTime(web.getEndDate()))
                .withTopic(MessageTopicWebToDomainMapper.map(web.getMessageTopic()))
                .withType(MessageTypeWebToDomainMapper.map(web.getMessageType()))
                .withContent(web.getContent())
                .build();
    }

    public static PagedConversationMessagesQuery map(PagedConversationMessageQuery web) {
        return PagedConversationMessagesQuery.newBuilder()
                .withConversationMessageQuery(map(web.getQuery()))
                .withQueryPage(PaginationWebToDomainMapper.map(web.getPage()))
                .build();
    }
}
