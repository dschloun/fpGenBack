package be.unamur.fpgen.mapper.domainToWeb.pagination;

import be.unamur.model.Pagination;
import be.unamur.model.QueryPage;

/**
 * This class is a mapper which map the domain object to the web object
 */
public class PaginationDomainToWebMapper {
    public static Pagination map(be.unamur.fpgen.pagination.Pagination domain) {
        return new Pagination()
                .page(domain.getPage())
                .size(domain.getSize())
                .totalSize(domain.getTotalSize());
    }

    public static QueryPage map(be.unamur.fpgen.pagination.QueryPage domain) {
        return new QueryPage()
                .page(domain.getPage())
                .size(domain.getSize());
    }
}
