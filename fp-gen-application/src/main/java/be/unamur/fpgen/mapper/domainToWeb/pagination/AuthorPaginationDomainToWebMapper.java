package be.unamur.fpgen.mapper.domainToWeb.pagination;

import be.unamur.fpgen.mapper.domainToWeb.AuthorDomainToWebMapper;
import be.unamur.fpgen.utils.MapperUtil;
import be.unamur.model.AuthorsPage;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class AuthorPaginationDomainToWebMapper {

    public static AuthorsPage map(be.unamur.fpgen.author.pagination.AuthorsPage domain) {
        return new AuthorsPage()
                .authors(MapperUtil.mapList(domain.getAuthorList(), AuthorDomainToWebMapper::map))
                .pagination(PaginationDomainToWebMapper.map(domain.getPagination()));
    }
}
