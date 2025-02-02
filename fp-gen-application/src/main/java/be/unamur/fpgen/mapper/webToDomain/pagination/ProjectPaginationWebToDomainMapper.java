package be.unamur.fpgen.mapper.webToDomain.pagination;

import be.unamur.fpgen.mapper.webToDomain.PaginationWebToDomainMapper;
import be.unamur.fpgen.mapper.webToDomain.ProjectTypeWebToDomainMapper;
import be.unamur.fpgen.project.pagination.PagedProjectsQuery;
import be.unamur.fpgen.project.pagination.ProjectQuery;
import be.unamur.fpgen.utils.DateUtil;
import be.unamur.model.ProjectType;

/**
 * This class is a mapper which map the web object to the domain object
 */
public class ProjectPaginationWebToDomainMapper {

    public static ProjectQuery map(be.unamur.model.ProjectQuery web){
        return ProjectQuery.newBuilder()
                .withType(ProjectTypeWebToDomainMapper.map(web.getProjectType()))
                .withName(web.getName())
                .withDescription(web.getDescription())
                .withAuthorTrigram(web.getAuthorTrigram())
                .withOrganization(web.getOrganization())
                .withStartDate(DateUtil.convertLocalDateToOffsetDateTime(web.getStartDate()))
                .withEndDate(DateUtil.convertLocalDateToOffsetDateTime(web.getEndDate()))
                .build();
    }

    public static PagedProjectsQuery map(be.unamur.model.PagedProjectQuery web){
        return PagedProjectsQuery.newBuilder()
                .withProjectQuery(map(web.getQuery()))
                .withQueryPage(PaginationWebToDomainMapper.map(web.getPage()))
                .build();
    }
}
