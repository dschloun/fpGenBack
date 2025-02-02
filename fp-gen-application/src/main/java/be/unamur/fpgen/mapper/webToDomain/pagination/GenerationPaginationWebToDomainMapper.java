package be.unamur.fpgen.mapper.webToDomain.pagination;



import be.unamur.fpgen.generation.pagination.GenerationQuery;
import be.unamur.fpgen.generation.pagination.PagedGenerationsQuery;
import be.unamur.fpgen.mapper.webToDomain.GenerationTypeWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTopicWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.MessageTypeWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.PaginationWebToDomainMapper;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.PagedGenerationQuery;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class GenerationPaginationWebToDomainMapper {

    public static GenerationQuery map(be.unamur.model.GenerationQuery web) {
        return GenerationQuery.newBuilder()
                .withStartDate(DateUtil.convertLocalDateToOffsetDateTime(web.getStartDate()))
                .withEndDate(DateUtil.convertLocalDateToOffsetDateTime(web.getEndDate()))
                .withMessageTopic(MessageTopicWebToDomainMapper.map(web.getMessageTopic()))
                .withMessageType(MessageTypeWebToDomainMapper.map(web.getMessageType()))
                .withGenerationType(GenerationTypeWebToDomainMapper.map(web.getGenerationType()))
                .withAuthorTrigram(web.getAuthorTrigram())
                .withNotInDatasetIdList(web.getNotInDatasetIdList())
                .withInDatasetIdList(web.getInDatasetIdList())
                .withQuantity(web.getQuantity())
                .withPromptVersion(web.getPromptVersion())
                .build();
    }

    public static PagedGenerationsQuery map(PagedGenerationQuery web) {
        return PagedGenerationsQuery.newBuilder()
                .withGenerationQuery(map(web.getQuery()))
                .withQueryPage(PaginationWebToDomainMapper.map(web.getPage()))
                .build();
    }
}
