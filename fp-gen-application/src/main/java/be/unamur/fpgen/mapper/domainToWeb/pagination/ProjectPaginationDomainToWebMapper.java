package be.unamur.fpgen.mapper.domainToWeb.pagination;

import be.unamur.fpgen.mapper.domainToWeb.ProjectDomainToWebMapper;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.ProjectsPage;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class ProjectPaginationDomainToWebMapper {
    public static ProjectsPage map(be.unamur.fpgen.project.pagination.ProjectsPage domain) {
        return new ProjectsPage()
                .projects(MapperUtil.mapList(domain.getProjectList(), ProjectDomainToWebMapper::map))
                .pagination(PaginationDomainToWebMapper.map(domain.getPagination()));
    }
}
